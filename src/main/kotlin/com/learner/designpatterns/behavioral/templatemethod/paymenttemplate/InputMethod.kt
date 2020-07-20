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
 * Wraps various messages and their corresponding request codes for
 * detailing the user about the required input.
 *
 * Created by Rishabh on 13-07-2020
 */
enum class InputMethod(val requestCode: Int, val requestMessage: String) {
    CONFIRM_PAYMENT_RECEIVED(1001, "Payment Received (y/n)"),
    CREDIT_CARD_NUMBER(1002, "Insert Credit Card"),
    CREDIT_CARD_PIN(1003, "Enter PIN"),
    USERNAME(1004, "Username"),
    PASSWORD(1005, "Password")
}