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

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Test-Cases for [PaymentTemplate]
 *
 * Created by Rishabh on 12-07-2020
 */
internal class PaymentTemplateTest {

    private val inputMethodHandler = mockk<(InputMethod) -> String>()

    /**
     * Tests that [PaymentTemplate.doTransaction] completes successfully and returns
     * instance of [TransactionDetail]
     */
    @Test
    fun `verify doTransaction() returns TransactionDetail`() {

        // asserts the operation results for success
        fun `assert doTransaction returns TransactionDetail`(paymentTemplate: PaymentTemplate) {
            val result = paymentTemplate.doTransaction(3000.0)
            Assertions.assertThat(result).isInstanceOf(TransactionDetail::class.java)
            Assertions.assertThat(result.timestamp).isNotNull()
            Assertions.assertThat(result.transactionId).isNotNull()
        }

        // assert for cash-payment
        every { inputMethodHandler.invoke(InputMethod.CONFIRM_PAYMENT_RECEIVED) } returns "y"
        `assert doTransaction returns TransactionDetail`(CashPaymentTemplate(inputMethodHandler))

        // assert for credit-card-payment
        every { inputMethodHandler.invoke(InputMethod.CREDIT_CARD_NUMBER) } returns "12345678"
        every { inputMethodHandler.invoke(InputMethod.CREDIT_CARD_PIN) } returns "0000"
        `assert doTransaction returns TransactionDetail`(CreditCardPaymentTemplate(inputMethodHandler))

        // assert for net-banking-template
        every { inputMethodHandler.invoke(InputMethod.USERNAME) } returns "rishabh"
        every { inputMethodHandler.invoke(InputMethod.PASSWORD) } returns "sharma"
        `assert doTransaction returns TransactionDetail`(NetBankingPaymentTemplate(inputMethodHandler))
    }

    /**
     * Tests that [PaymentTemplate.doTransaction] throws [Exception] when supplied invalid input
     */
    @Test
    fun `verify doTransaction() throws Exception`() {

        // asserts the operation for exception
        fun `assert doTransaction() throws Exception`(paymentTemplate: PaymentTemplate, amount: Double) =
            Assertions.assertThatThrownBy { paymentTemplate.doTransaction(amount) }

        // assert for cash-payment
        // => 1. when agent inputs negative response as "n"
        every { inputMethodHandler.invoke(InputMethod.CONFIRM_PAYMENT_RECEIVED) } returns "n"
        `assert doTransaction() throws Exception`(CashPaymentTemplate(inputMethodHandler), 3000.0)
        // => 1. when agent inputs negative response as "N"
        every { inputMethodHandler.invoke(InputMethod.CONFIRM_PAYMENT_RECEIVED) } returns "N"
        `assert doTransaction() throws Exception`(CashPaymentTemplate(inputMethodHandler), 3000.0)
        // => 2. when agent inputs no response as ""
        every { inputMethodHandler.invoke(InputMethod.CONFIRM_PAYMENT_RECEIVED) } returns ""
        `assert doTransaction() throws Exception`(CashPaymentTemplate(inputMethodHandler), 3000.0)

        // assert for credit-card-payment
        every { inputMethodHandler.invoke(InputMethod.CREDIT_CARD_NUMBER) } returns "12345678"
        // => 1. when agent machine reads correct card number, and user inputs wrong pin
        every { inputMethodHandler.invoke(InputMethod.CREDIT_CARD_NUMBER) } returns "1234"
        `assert doTransaction() throws Exception`(CreditCardPaymentTemplate(inputMethodHandler), 3000.0)
        // => 2. when agent machine reads correct card number, and user inputs wrong pin
        every { inputMethodHandler.invoke(InputMethod.CREDIT_CARD_NUMBER) } returns "000"
        `assert doTransaction() throws Exception`(CreditCardPaymentTemplate(inputMethodHandler), 3000.0)
        // => 3. when agent machine reads correct card number, and user inputs wrong pin
        every { inputMethodHandler.invoke(InputMethod.CREDIT_CARD_NUMBER) } returns "00000"
        `assert doTransaction() throws Exception`(CreditCardPaymentTemplate(inputMethodHandler), 3000.0)
        // => 4. when agent machine reads correct card number, and user inputs no pin
        every { inputMethodHandler.invoke(InputMethod.CREDIT_CARD_NUMBER) } returns ""
        `assert doTransaction() throws Exception`(CreditCardPaymentTemplate(inputMethodHandler), 3000.0)

        // assert for net-banking
        // => 1. when user inputs no username
        every { inputMethodHandler.invoke(InputMethod.USERNAME) } returns ""
        `assert doTransaction() throws Exception`(NetBankingPaymentTemplate(inputMethodHandler), 3000.0)
        // => 2. when user inputs wrong username, but no password
        every { inputMethodHandler.invoke(InputMethod.USERNAME) } returns "abc"
        every { inputMethodHandler.invoke(InputMethod.PASSWORD) } returns ""
        `assert doTransaction() throws Exception`(NetBankingPaymentTemplate(inputMethodHandler), 3000.0)
        // => 3. when user inputs wrong username and wrong password
        every { inputMethodHandler.invoke(InputMethod.USERNAME) } returns "abc"
        every { inputMethodHandler.invoke(InputMethod.PASSWORD) } returns "xyz"
        `assert doTransaction() throws Exception`(NetBankingPaymentTemplate(inputMethodHandler), 3000.0)
        // => 4. when user inputs correct username and wrong password
        every { inputMethodHandler.invoke(InputMethod.USERNAME) } returns "rishabh"
        every { inputMethodHandler.invoke(InputMethod.PASSWORD) } returns "xyz"
        `assert doTransaction() throws Exception`(NetBankingPaymentTemplate(inputMethodHandler), 3000.0)
    }
}