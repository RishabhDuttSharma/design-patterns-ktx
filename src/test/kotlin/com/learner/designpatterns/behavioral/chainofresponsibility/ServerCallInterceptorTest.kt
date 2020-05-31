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
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Test Cases for [ServerCallInterceptor]
 *
 * Created by Rishabh on 31-05-2020
 */
internal class ServerCallInterceptorTest {

    /**
     * Verifies that the [ServerCallInterceptor] executes a request and
     * returns corresponding response
     */
    @Test
    fun intercept() {

        // create request-body
        val targetRequestBody = EncoderDecoder.encode(RequestBody.GET_USER.name)
        // create expected response-body
        val expectedResponseBody = EncoderDecoder.encode(WebApiServer.RESPONSE_GET_USER)

        // create mock interceptor chain
        mockk<InterceptorChain>().apply {
            // mock request
            every { request } returns Request(targetRequestBody)
            // intercept the call, and execute API-Call
        }.let(ServerCallInterceptor::intercept).run {
            // returned response-body should match expected response-body
            Assertions.assertThat(body).isEqualTo(expectedResponseBody)
        }
    }
}