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

package com.learner.designpatterns.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Returns the Base64-encoded value
 */
fun String.base64Encode(): String =
    Base64.getEncoder().encodeToString(toByteArray())

/**
 * Returns the Base64-decoded value
 */
fun String.base64Decode(): String =
    String(Base64.getDecoder().decode(this))

/**
 * Returns the formatted-date
 */
fun Long.formatToDate(dateFormat: String): String = Calendar.getInstance()
    .also { it.timeInMillis = this }.time
    .let(SimpleDateFormat(dateFormat)::format)