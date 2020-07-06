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

import java.text.SimpleDateFormat
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

data class PaymentDetail(
    val transactionId: String,
    val timestamp: Long
)

abstract class PaymentTemplate {

    fun startPayment(amount: Double) {

        Thread.sleep(2000)

        val authResult = authorize()
        Thread.sleep(2000)
        if (authResult is PaymentResult.Error) {
            conclude(authResult.message)
            return
        }

        val paymentResult = payment(authResult.data!!, amount)
        Thread.sleep(2000)
        if (paymentResult is PaymentResult.Error) {
            conclude(paymentResult.message)
            return
        }

        paymentResult.data!!.let {
            val transactionId = paymentResult.data.transactionId
            val dateTime = SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(
                Calendar.getInstance().apply {
                    timeInMillis = paymentResult.data.timestamp
                }.time
            )
            String.format("Transaction successful\nId: $transactionId\nDate: $dateTime")
        }.let(::conclude)
    }

    protected abstract fun authorize(): PaymentResult<AuthorizationToken>

    protected abstract fun payment(authToken: AuthorizationToken, amount: Double): PaymentResult<PaymentDetail>

    protected abstract fun conclude(result: String)
}

class CreditCardPaymentTemplate : PaymentTemplate() {

    override fun authorize(): PaymentResult<AuthorizationToken> {
        val scanner = Scanner(System.`in`)
        println("Enter PIN")
        val pin = scanner.nextLine()
        if (pin.isNullOrEmpty())
            return PaymentResult.Error("Incorrect PIN")

        val authToken = Base64.getEncoder().encodeToString(pin.toByteArray())
        return AuthorizationToken(authToken, 1_20_000).let {
            PaymentResult.Success(it, "authorized")
        }
    }

    override fun payment(authToken: AuthorizationToken, amount: Double): PaymentResult<PaymentDetail> {
        println("Payment for INR $amount is in progress...")
        val timeStamp = System.currentTimeMillis()
        val transactionId = Base64.getEncoder().encodeToString(timeStamp.toString().toByteArray())
        val paymentDetail = PaymentDetail(transactionId, timeStamp)
        return PaymentResult.Success(paymentDetail, "Transaction successful.")
    }

    override fun conclude(result: String) {
        println(result)
    }
}

class CashPaymentTemplate : PaymentTemplate() {

    override fun authorize(): PaymentResult<AuthorizationToken> {
        return PaymentResult.Success(AuthorizationToken("", 0), "authorized")
    }

    override fun payment(authToken: AuthorizationToken, amount: Double): PaymentResult<PaymentDetail> {
        print("Payment Received (y/n): ")
        val received = Scanner(System.`in`).nextLine().toString().startsWith("y", true)
        if (received) {
            val timeStamp = System.currentTimeMillis()
            val transactionId = Base64.getEncoder().encodeToString(timeStamp.toString().toByteArray())
            val paymentDetail = PaymentDetail(transactionId, timeStamp)
            return PaymentResult.Success(paymentDetail, "Transaction successful.")
        }
        return PaymentResult.Error("Payment not received")
    }

    override fun conclude(result: String) {
        println(result)
    }

}

fun main() {
//    CreditCardPaymentTemplate().startPayment(3000.0)
    CashPaymentTemplate().startPayment(3000.0)
}