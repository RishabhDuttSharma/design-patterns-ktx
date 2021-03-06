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
 * [PaymentTemplate] for implementing payment via Net-Banking
 *
 * - Asks end-user to enter Username and Password
 * - Invoke WebServer to validate and retrieve the authorization-token
 * - Invoke WebServer to perform transaction for given amount
 * - Inform the User to close the Session
 */
class NetBankingPaymentTemplate(private val inputMethodHandler: (InputMethod) -> String) : PaymentTemplate() {

    /** initial set-up */
    override fun initialize() = println("Paying via Net-Banking")

    /** perform authorization */
    override fun authorize(): PaymentResult<AuthorizationToken> {
        // ask user to enter username
        val username = inputMethodHandler(InputMethod.USERNAME)
        // check username and return error, if invalid
        if (username.isEmpty()) {
            return PaymentResult.Error("Invalid username")
        }

        // ask user to enter password
        val password = inputMethodHandler(InputMethod.PASSWORD)
        // check password and return error, if invalid
        if (password.isEmpty()) {
            return PaymentResult.Error("Invalid password")
        }
        // ask web-server to validate user credentials, and return auth-token
        return WebApiServer.authorize(username, password)
    }

    /** perform transaction */
    override fun transact(authToken: AuthorizationToken, amount: Double) =
        WebApiServer.doTransaction(authToken.token, amount)

    /** show information on result */
    override fun conclude() = println("Please close the session.")
}