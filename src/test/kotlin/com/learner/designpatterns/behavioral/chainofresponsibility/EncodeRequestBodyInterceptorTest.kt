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

package com.learner.designpatterns.behavioral.chainofresponsibility

import io.mockk.every
import io.mockk.mockkObject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Test Case for [EncodeRequestBodyInterceptor]
 *
 * Created by Rishabh on 31-05-2020
 */
internal class EncodeRequestBodyInterceptorTest {

    /**
     * Verifies
     */
    @Test
    fun intercept() {

        val testRequest = Request(RequestBody.GET_USER.name)
        val testResponse = Response(WebApiServer.RESPONSE_GET_USER)

        InterceptorChain(testRequest).apply {
            // mock current-chain
            mockkObject(this)
            // mock response for chain
            every { proceed() } returns testResponse
        }.also { chain ->
            // intercept chain in EncodeRequestBodyInterceptor
            EncodeRequestBodyInterceptor.intercept(chain)
        }.request.run {
            // request-body should be encrypted
            assertThat(body).isEqualTo(EncoderDecoder.encode(RequestBody.GET_USER.name))
        }
    }
}