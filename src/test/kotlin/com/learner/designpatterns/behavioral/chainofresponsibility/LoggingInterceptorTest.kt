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

import com.learner.designpatterns.BasePrintStreamTest
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Test Cases for [LoggingInterceptor]
 *
 * Created by Rishabh on 31-05-2020
 */
internal class LoggingInterceptorTest : BasePrintStreamTest() {


    /**
     * Verifies that the LoggingInterceptor logs request and response
     */
    @Test
    fun intercept() {

        val testRequest = Request(RequestBody.GET_USER.name)
        val testResponse = Response(WebApiServer.RESPONSE_GET_USER)

        // mock-chain
        val chain = mockk<InterceptorChain>().apply {
            // mock request
            every { request } returns testRequest
            // mock response
            every { proceed() } returns (testResponse)
        }

        // intercept mocked-chain
        LoggingInterceptor.intercept(chain)

        // verify console for output
        getConsoleOutput().run {
            // request-body should be printed
            Assertions.assertThat(this).contains("Request-Body : ${RequestBody.GET_USER.name}")
            // response-body should be printed
            Assertions.assertThat(this).contains("Response-Body : ${WebApiServer.RESPONSE_GET_USER}")
        }
    }
}