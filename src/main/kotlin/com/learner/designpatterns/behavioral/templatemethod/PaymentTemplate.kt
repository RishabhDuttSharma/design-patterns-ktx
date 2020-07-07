/*
 * Copyright 2020 Learner Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.learner.designpatterns.behavioral.templatemethod

import com.learner.designpatterns.util.base64Decode
import com.learner.designpatterns.util.base64Encode
import com.learner.designpatterns.util.formatToDate
import java.util.*

/**
 *
 * Created by Rishabh on 01-07-2020
 */

sealed class PaymentResult<T>(
    val message: String,
    val data: T? = null
) {
    class Success<T>(data: T, message: String) : PaymentResult<T>(message, data)

    class Error<T>(message: String) : PaymentResult<T>(message)
}

data class AuthorizationToken(
    val token: String,
    val expires: Long
)

data class TransactionDetail(
    val transactionId: String,
    val timestamp: Long
)

abstract class PaymentTemplate {

    fun startPayment(amount: Double) {

        val authResult = authorize()
        if (authResult is PaymentResult.Error) {
            return conclude(authResult)
        }

        val paymentResult = payment(authResult.data!!, amount)
        if (paymentResult is PaymentResult.Error) {
            return conclude(paymentResult)
        }

        // show final payment result
        conclude(paymentResult)

        // print transaction-detail
        paymentResult.data?.let {
            val transactionId = it.transactionId
            val formattedDate = it.timestamp.formatToDate("dd-MM-yyyy hh:mm:ss")
            println("Transaction Detail\nId: $transactionId\nDated: $formattedDate")
        }
    }

    protected abstract fun authorize(): PaymentResult<AuthorizationToken>

    protected abstract fun payment(authToken: AuthorizationToken, amount: Double): PaymentResult<TransactionDetail>

    protected abstract fun conclude(result: PaymentResult<*>)
}

class CreditCardPaymentTemplate(private val cardNumber: String) : PaymentTemplate() {

    override fun authorize(): PaymentResult<AuthorizationToken> {
        println("Enter PIN")
        val pin = Scanner(System.`in`).nextLine()
        if (pin.isNullOrEmpty()) {
            return PaymentResult.Error("Invalid PIN")
        }
        return WebApiServer.authorize(cardNumber, pin)
    }

    override fun payment(authToken: AuthorizationToken, amount: Double): PaymentResult<TransactionDetail> {
        return WebApiServer.doPayment(authToken.token, amount)
    }

    override fun conclude(result: PaymentResult<*>) = when (result) {
        is PaymentResult.Success -> println("Payment successful for Credit Card $cardNumber")
        else -> println("Payment Failed: ${result.message}")
    }
}

class CashPaymentTemplate : PaymentTemplate() {

    override fun authorize() = WebApiServer.authorizeGuest()

    override fun payment(authToken: AuthorizationToken, amount: Double): PaymentResult<TransactionDetail> {
        print("Payment Received (y/n): ")
        if (!Scanner(System.`in`).nextLine().toString().startsWith("y", true)) {
            return PaymentResult.Error("Payment not received")
        }
        return WebApiServer.doPayment(authToken.token, amount)
    }

    override fun conclude(result: PaymentResult<*>) = when (result) {
        is PaymentResult.Success -> "Success: ${result.message}"
        else -> "Error: ${result.message}"
    }.let(::println)
}

class NetBankingPaymentTemplate : PaymentTemplate() {

    override fun authorize(): PaymentResult<AuthorizationToken> {

        val scanner = Scanner(System.`in`)

        print("Username: ")
        val username = scanner.nextLine().toString()
        if (username.isEmpty()) {
            return PaymentResult.Error("invalid username")
        }

        print("Password: ")
        val password = scanner.nextLine().toString()
        if (password.isEmpty()) {
            return PaymentResult.Error("invalid password")
        }

        return WebApiServer.authorize(username, password)
    }

    override fun payment(authToken: AuthorizationToken, amount: Double): PaymentResult<TransactionDetail> {
        return WebApiServer.doPayment(authToken.token, amount)
    }

    override fun conclude(result: PaymentResult<*>) = println("NetBanking: ${result.message}")
}

fun main() {
//    CreditCardPaymentTemplate("12345678").startPayment(3000.0)
    CashPaymentTemplate().startPayment(3000.0)
//    NetBankingPaymentTemplate().startPayment(3000.0)
}

object WebApiServer {

    private const val DEF_EXPIRES = 1_20_000L

    private const val MESSAGE_AUTH_SUCCESS = "authorized"
    private const val MESSAGE_INVALID_CREDENTIALS = "invalid credentials"
    private const val MESSAGE_INVALID_TOKEN = "invalid token"
    private const val MESSAGE_TRANSACTION_SUCCESSFUL = "transaction successful"

    private val authTable = mutableMapOf(
        "rishabh" to "sharma",
        "12345678" to "0000",
        "guest" to ""
    )

    private val transactionTable = mutableMapOf<String, Double>()

    fun authorize(id: String, password: String): PaymentResult<AuthorizationToken> {
        Thread.sleep(2000)
        if (!validate(id, password)) {
            return PaymentResult.Error(MESSAGE_INVALID_CREDENTIALS)
        }
        return PaymentResult.Success(createToken(id), MESSAGE_AUTH_SUCCESS)
    }

    fun authorizeGuest() = authorize("guest", "")

    fun doPayment(token: String, amount: Double): PaymentResult<TransactionDetail> {
        Thread.sleep(2000)
        if (!authTable.containsKey(token.base64Decode())) {
            return PaymentResult.Error(MESSAGE_INVALID_TOKEN)
        }
        return PaymentResult.Success(createTransaction(amount), MESSAGE_TRANSACTION_SUCCESSFUL)
    }

    private fun validate(id: String, password: String) =
        authTable.containsKey(id) && authTable[id] == password

    private fun createToken(id: String): AuthorizationToken =
        AuthorizationToken(id.base64Encode(), DEF_EXPIRES)

    private fun createTransaction(amount: Double): TransactionDetail {
        val timeMillis = System.currentTimeMillis()
        val transactionId = timeMillis.base64Encode()
        transactionTable[transactionId] = amount
        return TransactionDetail(transactionId, timeMillis)
    }
}