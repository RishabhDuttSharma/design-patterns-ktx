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
 * Model class to hold Payment Result
 *
 * @param message the message of the result
 * @param data the result of the process
 *
 * Created by Rishabh on 09-07-2020
 */
sealed class PaymentResult<T>(
    val message: String,
    val data: T? = null
) {

    /** Represents the Success form of PaymentResult */
    class Success<T>(data: T, message: String) : PaymentResult<T>(message, data)

    /** Represents the Error form of PaymentResult */
    class Error<T>(message: String) : PaymentResult<T>(message)
}

/**
 * Model class to hold the authorization-token to be used
 * for Payment-requests on-behalf of the User
 *
 * @param token the authorization-token
 * @param expires the life of authorization-token in millis
 */
data class AuthorizationToken(
    val token: String,
    val expires: Long
)

/**
 * Model class to hold the Transaction-detail of the payment
 *
 * @param transactionId the unique-id for a given payment
 * @param timestamp the time at which the transaction is committed
 */
data class TransactionDetail(
    val transactionId: String,
    val timestamp: Long
)