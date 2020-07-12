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

import com.learner.designpatterns.util.base64Decode
import com.learner.designpatterns.util.base64Encode

/**
 * Represents a Mock Web-Server that executes a request and then return a response
 */
object WebApiServer {

    private const val DEF_EXPIRES = 1_20_000L

    private const val MESSAGE_AUTH_SUCCESS = "authorized"
    private const val MESSAGE_INVALID_CREDENTIALS = "Invalid credentials"
    private const val MESSAGE_INVALID_TOKEN = "Invalid token"
    private const val MESSAGE_TRANSACTION_SUCCESSFUL = "Transaction successful"

    private val authTable = mutableMapOf(
        "rishabh" to "sharma",
        "12345678" to "0000",
        "guest" to ""
    )

    private val transactionTable = mutableMapOf<String, Double>()

    fun authorize(id: String, password: String): PaymentResult<AuthorizationToken> =
        if (!validate(id, password)) {
            PaymentResult.Error(MESSAGE_INVALID_CREDENTIALS)
        } else {
            PaymentResult.Success(createToken(id), MESSAGE_AUTH_SUCCESS)
        }

    fun authorizeGuest() = authorize("guest", "")

    fun doTransaction(token: String, amount: Double): PaymentResult<TransactionDetail> =
        if (!authTable.containsKey(token.base64Decode())) {
            PaymentResult.Error(MESSAGE_INVALID_TOKEN)
        } else {
            PaymentResult.Success(createTransaction(amount), MESSAGE_TRANSACTION_SUCCESSFUL)
        }

    private fun validate(id: String, password: String) =
        authTable.containsKey(id) && authTable[id] == password

    private fun createToken(id: String): AuthorizationToken =
        AuthorizationToken(id.base64Encode(), DEF_EXPIRES)

    private fun createTransaction(amount: Double): TransactionDetail {
        val timeMillis = System.currentTimeMillis()
        val transactionId = timeMillis.base64Encode()
        transactionTable[transactionId] = amount
        return TransactionDetail(transactionId, timeMillis)
    }
}