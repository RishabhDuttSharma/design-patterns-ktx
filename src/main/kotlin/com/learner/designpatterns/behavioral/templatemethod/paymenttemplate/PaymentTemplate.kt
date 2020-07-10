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

/**
 * Payment Template
 * ----------------
 *
 * A retail invoicing-system may support various payment-systems. It reduces
 * the chances of a customer-conflict during payment.
 *
 * For making a payment, there are certain steps that are followed till its
 * completion e.g., initializing pre-requisites for further steps, taking
 * authorization(on-behalf of Customer) for making payment, registering the
 * payment at a centralized-database and then printing the transaction detail
 * for the same.
 *
 * To be able to invoke several types of payment-systems through these steps,
 * a [PaymentTemplate] can be defined. The [PaymentTemplate] defines all the
 * necessary steps as abstract methods. Along with these abstracts methods,
 * it defines another method([beginTransaction]) with definition that calls
 * all the other methods in the required order.
 *
 * Created by Rishabh on 01-07-2020
 */
abstract class PaymentTemplate {

    fun beginTransaction(amount: Double) {

        // => 1. perform initialization
        initialize()

        // => 2. perform authorization
        val authResult = authorize()
        // show authorization result
        conclude(authResult)
        // return if authorization is failed
        if (authResult is PaymentResult.Error) return

        // data in result would be non-null for Success
        val authToken = authResult.data!!

        // => 3. perform transaction
        val paymentResult = transact(authToken, amount)
        // show payment-result irrespective its status
        conclude(paymentResult)
        // return if payment is failed
        if (paymentResult is PaymentResult.Error) return

        // print transaction-detail
        paymentResult.data!!.let {
            val transactionId = it.transactionId
            val formattedDate = it.timestamp.formatToDate("dd-MM-yyyy hh:mm:ss")
            println("Transaction Detail\nId: $transactionId\nDated: $formattedDate")
        }
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
     * Sends user-information to a WebServer to receive a authorization-token
     * for making further payment related requests.
     */
    protected abstract fun authorize(): PaymentResult<AuthorizationToken>

    /**
     * Step 3: Payment
     *
     * Sends authorization-token along with amount to WebApiServer for invoking
     * payment-transaction
     */
    protected abstract fun transact(authToken: AuthorizationToken, amount: Double): PaymentResult<TransactionDetail>

    /**
     * Intermediary Step: Conclude
     *
     * Shows result of processing after completion of each step.
     */
    protected abstract fun conclude(result: PaymentResult<*>)
}

/** playground */
fun main() {
    arrayOf(
        CreditCardPaymentTemplate { "12345678" },
        CashPaymentTemplate(),
        NetBankingPaymentTemplate()
    ).random().beginTransaction(3000.0)
}

