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
 *
 * Created by Rishabh on 01-07-2020
 */
abstract class PaymentTemplate {

    fun startPayment(amount: Double) {

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
     * Step 4: Conclude
     *
     * Shows results to user after completion of each step.
     * In case of errors, intermediate steps would automatically be skipped.
     */
    protected abstract fun conclude(result: PaymentResult<*>)
}

fun main() {
    arrayOf(
        CreditCardPaymentTemplate { "12345678" },
        CashPaymentTemplate(),
        NetBankingPaymentTemplate()
    ).random().startPayment(3000.0)
}

