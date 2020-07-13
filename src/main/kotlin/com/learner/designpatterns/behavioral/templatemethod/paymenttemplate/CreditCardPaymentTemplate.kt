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
 * [PaymentTemplate] for implementing payment via Credit-Card using a Credit-Card Reader
 *
 * - Scan credit-card details via Card-Reading-Device
 * - Ask user to enter Credit-Card PIN
 * - WebServer is invoked for validating Card-Details and retrieving the authorization-token
 * - WebServer is invoked for making a transaction for given amount
 * - Transaction-detail is presented to end-user
 *
 * @param creditCardReader device that reads credit-card and returns its number
 */
class CreditCardPaymentTemplate(private val creditCardReader: (InputMethod) -> String) : PaymentTemplate() {

    private lateinit var cardNumber: String

    /** initial set-up -> read card-number */
    override fun initialize() {
        // read credit-card number from reader-device
        cardNumber = creditCardReader(InputMethod.CREDIT_CARD_NUMBER)
        // show information text
        println("Paying via Credit Card ***${cardNumber.takeLast(3)}")
    }

    /** perform authorization */
    override fun authorize(): PaymentResult<AuthorizationToken> {
        // ask user to enter PIN
        val pin = creditCardReader(InputMethod.CREDIT_CARD_PIN)
        // validate if a valid PIN is entered
        if (pin.isEmpty()) {
            return PaymentResult.Error("Invalid PIN")
        }
        // asks server to validate, and return authorization-token
        return WebApiServer.authorize(cardNumber, pin)
    }

    /** perform transaction */
    override fun transact(authToken: AuthorizationToken, amount: Double) =
        WebApiServer.doTransaction(authToken.token, amount)

    /** show information on result */
    override fun conclude() = println("Safe to remove Credit Card")
}