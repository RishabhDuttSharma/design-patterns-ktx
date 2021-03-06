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

/**
 * [PaymentTemplate] implementation for making payment via Cash
 *
 * - User makes payment via Cash
 * - Agent confirms if cash is received
 * - WebServer is invoked for retrieving the guest-authorization-token
 * - WebServer is invoked for creating respective transaction
 * - Transaction-detail is presented to end-user
 */
class CashPaymentTemplate(private val confirmationProvider: (InputMethod) -> String) : PaymentTemplate() {

    /** initial set-up */
    override fun initialize() = println("Paying via Cash")

    /** perform authorization */
    override fun authorize() = WebApiServer.authorizeGuest()

    /** perform transaction */
    override fun transact(authToken: AuthorizationToken, amount: Double): PaymentResult<TransactionDetail> {
        // payment initiated. ask if payment-amount is received
        val agentInput = confirmationProvider(InputMethod.CONFIRM_PAYMENT_RECEIVED)
        // return payment-error if payment is denied
        if (!agentInput.startsWith("y", true)) {
            return PaymentResult.Error("Payment not received")
        }
        // continue registering transaction on the WebServer
        return WebApiServer.doTransaction(authToken.token, amount)
    }

    /** show information on result */
    override fun conclude() = println("Thank you!")
}