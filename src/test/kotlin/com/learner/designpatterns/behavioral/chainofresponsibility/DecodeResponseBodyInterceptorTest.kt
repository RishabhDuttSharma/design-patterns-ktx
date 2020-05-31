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
 * Test Case for [DecodeResponseBodyInterceptor]
 *
 * Created by Rishabh on 31-05-2020
 */
internal class DecodeResponseBodyInterceptorTest {

    /**
     * Verifies that the response is decoded correctly by the
     * DecodeResponseBodyInterceptor
     */
    @Test
    fun intercept() {

        // response body for test
        val testResponseBody = "test-response-body";

        // create mock chain
        val chain = mockk<InterceptorChain>().apply {
            // mock request
            every { request } returns Request(RequestBody.GET_USER.name)
            // mock response on proceed
            val encodedResponseBody = EncoderDecoder.encode(testResponseBody)
            every { proceed() } returns Response(encodedResponseBody)
        }

        // intercept the chain for decoding response
        DecodeResponseBodyInterceptor.intercept(chain).run {
            // returned response-body should match test-response-body
            Assertions.assertThat(body).isEqualTo(testResponseBody)
        }
    }
}