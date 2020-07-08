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

class NetBankingPaymentTemplate : PaymentTemplate() {

    override fun initialize() = println("Paying via Net-Banking")

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

    override fun transact(authToken: AuthorizationToken, amount: Double) =
        WebApiServer.doTransaction(authToken.token, amount)

    override fun conclude(result: PaymentResult<*>) = println("NetBanking: ${result.message}")
}