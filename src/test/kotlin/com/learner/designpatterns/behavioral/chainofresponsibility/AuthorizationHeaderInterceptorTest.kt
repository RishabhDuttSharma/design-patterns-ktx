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
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Test Cases for [AuthorizationHeaderInterceptor]
 *
 * Created by Rishabh on 31-05-2020
 */
internal class AuthorizationHeaderInterceptorTest {

    /**
     * Verifies that the Headers are set to Request in chain after
     * intercept by AuthorizationHeaderInterceptor
     */
    @Test
    fun intercept() {

        // create mock chain
        val chain = mockk<InterceptorChain>().apply {
            // create mock request
            every { request } returns Request("unknown")
            // create mock response
            every { proceed() } returns Response(WebApiServer.RESPONSE_UNKNOWN)
        }

        // intercept the chain, and add authorization-headers
        AuthorizationHeaderInterceptor.intercept(chain)

        // chain-request-headers should have authorization
        assertThat(chain.request.headers).isNotNull
        assertThat(chain.request.headers).containsEntry("authorization", "access_token")
    }
}