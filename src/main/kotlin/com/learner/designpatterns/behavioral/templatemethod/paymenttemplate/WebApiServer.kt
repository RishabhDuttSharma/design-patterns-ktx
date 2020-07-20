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
 * Represents a mock Web-Server that receives requests, executes it, and then returns response
 */
object WebApiServer {

    private const val AUTH_TOKEN_EXPIRES = 1_20_000L

    // web-server message properties
    private const val MESSAGE_AUTH_SUCCESS = "authorized"
    private const val MESSAGE_INVALID_CREDENTIALS = "Invalid credentials"
    private const val MESSAGE_INVALID_TOKEN = "Invalid token"
    private const val MESSAGE_TRANSACTION_SUCCESSFUL = "Transaction successful"

    // represents a authorization-table in database
    private val authTable = mutableMapOf(
        "rishabh" to "sharma",
        "12345678" to "0000",
        "guest" to ""
    )

    // represents a transaction-table in database
    private val transactionTable = mutableMapOf<String, Double>()

    /**
     * Performs authorization of the entity with [id] and [password]
     *
     * @param id the unique identifier for entity
     * @param password the password corresponding to the [id]
     *
     * @return the [AuthorizationToken], if [id] and [password] combination is valid, error otherwise
     */
    fun authorize(id: String, password: String): PaymentResult<AuthorizationToken> =
        if (!validate(id, password)) {
            PaymentResult.Error(MESSAGE_INVALID_CREDENTIALS)
        } else {
            PaymentResult.Success(createToken(id), MESSAGE_AUTH_SUCCESS)
        }

    /**
     * Performs authorization for a guest-user, and generates a token for the same.
     *
     * @return the [AuthorizationToken] for a guest-user
     */
    fun authorizeGuest() = authorize("guest", "")

    /**
     * Creates a transaction for given [token], and payment [amount]
     *
     * @param token the valid authorization-token-value for an entity
     * @param amount the payment amount
     *
     * @return the [TransactionDetail] if token is valid, error otherwise
     */
    fun doTransaction(token: String, amount: Double): PaymentResult<TransactionDetail> =
        if (!authTable.containsKey(token.base64Decode())) {
            PaymentResult.Error(MESSAGE_INVALID_TOKEN)
        } else {
            PaymentResult.Success(createTransaction(amount), MESSAGE_TRANSACTION_SUCCESSFUL)
        }

    /**
     * Validates [id] and [password] for an entity
     */
    private fun validate(id: String, password: String) =
        authTable.containsKey(id) && authTable[id] == password

    /**
     * Generates a [AuthorizationToken] for given [id]
     */
    private fun createToken(id: String): AuthorizationToken =
        AuthorizationToken(id.base64Encode(), AUTH_TOKEN_EXPIRES)

    /**
     * Creates [TransactionDetail] for given payment [amount]
     */
    private fun createTransaction(amount: Double): TransactionDetail {
        val timeMillis = System.currentTimeMillis()
        val transactionId = timeMillis.toString().base64Encode()
        transactionTable[transactionId] = amount
        return TransactionDetail(transactionId, timeMillis)
    }
}