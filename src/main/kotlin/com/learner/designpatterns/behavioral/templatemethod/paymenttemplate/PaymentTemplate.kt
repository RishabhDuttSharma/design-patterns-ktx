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

package com.learner.designpatterns.behavioral.templatemethod.paymenttemplate

import com.learner.designpatterns.util.formatToDate
import java.util.*

/**
 * Payment-Template
 * ----------------
 *
 * A retail invoicing-system may support various payment-methods. It reduces
 * the chances of a customer-conflict during payment.
 *
 * For making a payment, there are certain steps that are followed till its
 * completion e.g., initializing pre-requisites for further steps, taking
 * authorization(on-behalf of Customer) for making payment, registering the
 * payment at a centralized-database and then printing the transaction detail
 * for the same.
 *
 * To be able to invoke several types of payment-methods through these steps,
 * a [PaymentTemplate] can be defined. The [PaymentTemplate] defines all the
 * necessary steps as abstract methods. Along with these abstracts methods,
 * it defines another method([doTransaction]) with definition that calls
 * all the other methods in the required order.
 *
 * Created by Rishabh on 01-07-2020
 */
abstract class PaymentTemplate {

    /**
     * Template-method that executes other steps of the transaction in-order
     *
     * @param amount the payment applicable
     * @throws PaymentException if payment is not successful
     * @return the [TransactionDetail] if payment is successful, throws [Exception] otherwise
     */
    @Throws(PaymentException::class)
    fun doTransaction(amount: Double): TransactionDetail {

        // => 1. perform initialization
        initialize()

        // => 2. perform authorization
        val authToken = authorize()
            // throw exception if authorization is failed
            .getResultOrThrowException()

        // => 3. perform transaction
        val transactionDetail = transact(authToken, amount)
            // throw exception if payment is failed
            .getResultOrThrowException()

        // => 4. conclude
        conclude()

        // return transaction-detail
        return transactionDetail
    }

    /**
     * Step 1: Initialize
     *
     * Performs initial-setup for payment
     */
    protected abstract fun initialize()

    /**
     * Step 2: Authorize
     *
     * Sends user-information to the WebServer to receive a authorization-token
     * for making further payment related requests.
     *
     * @return [PaymentResult.Success] with [AuthorizationToken] on success,
     * [PaymentResult.Error] otherwise
     */
    protected abstract fun authorize(): PaymentResult<AuthorizationToken>

    /**
     * Step 3: Payment
     *
     * Sends authorization-token along with amount to WebApiServer for invoking
     * payment-transaction
     *
     * @param authToken the token received during [authorize]
     * @param amount the amount for transaction
     *
     * @return [PaymentResult.Success] with [TransactionDetail] on success,
     * [PaymentResult.Error] otherwise
     */
    protected abstract fun transact(authToken: AuthorizationToken, amount: Double): PaymentResult<TransactionDetail>

    /**
     * Step 4: Conclude
     *
     * Release any resources held during transaction, or show important information
     * corresponding the transaction
     */
    protected abstract fun conclude()


    /** Thrown when an error is occurred during payment-process */
    class PaymentException(message: String) : Exception(message)

    /**
     * Returns the result in [PaymentResult.data], if [PaymentResult.Success],
     * throws [Exception] otherwise
     */
    private fun <T> PaymentResult<T>.getResultOrThrowException(): T =
        if (this is PaymentResult.Error) throw PaymentException(message) else data!!
}

/** playground */
fun main() {

    // use system-console as input-handler
    val inputMethodHandler: (InputMethod) -> String = {
        print("${it.requestMessage}: ")
        Scanner(System.`in`).nextLine().toString()
    }

    try {
        // creates an array of available payment-systems
        arrayOf(
            CreditCardPaymentTemplate(inputMethodHandler),
            CashPaymentTemplate(inputMethodHandler),
            NetBankingPaymentTemplate(inputMethodHandler)
        ).random()  // select a random payment-system, and invoke transaction
            .doTransaction(3000.0).let {
                // print transaction-detail
                val transactionId = it.transactionId
                val formattedDate = it.timestamp.formatToDate("dd-MM-yyyy hh:mm:ss")
                println("Transaction Detail\nId: $transactionId\nDated: $formattedDate")
            }
    } catch (e: PaymentTemplate.PaymentException) {
        // show with message that the payment is failed
        println("Payment Failed: ${e.message}")
    }
}