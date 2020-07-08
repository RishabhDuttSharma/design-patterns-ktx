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

import java.util.*

class CreditCardPaymentTemplate(private val creditCardReader: () -> String) : PaymentTemplate() {

    private lateinit var cardNumber: String

    override fun initialize() {
        cardNumber = creditCardReader()
        println("Paying via Credit Card")
    }

    override fun authorize(): PaymentResult<AuthorizationToken> {
        println("Enter PIN")
        val pin = Scanner(System.`in`).nextLine()
        if (pin.isNullOrEmpty()) {
            return PaymentResult.Error("Invalid PIN")
        }
        return WebApiServer.authorize(cardNumber, pin)
    }

    override fun transact(authToken: AuthorizationToken, amount: Double): PaymentResult<TransactionDetail> {
        return WebApiServer.doTransaction(
            authToken.token,
            amount
        )
    }

    override fun conclude(result: PaymentResult<*>) = when (result) {
        is PaymentResult.Success -> println("Payment successful for Card ***${cardNumber.takeLast(3)}")
        else -> println("Payment Failed: ${result.message}")
    }
}