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

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

/**
 * Test Cases for [InterceptorChain]
 *
 * Created by Rishabh on 27-05-2020
 *
 * @see InterceptorChain
 */
internal class InterceptorChainTest {

    /**
     * Verifies that InterceptorChain throws exception when supplied no interceptors.
     */
    @Test
    fun proceed_noInterceptor_throwException() {
        assertThrows<Exception>("Reached end-of-chain! Response responsibility interceptor not found.") {
            val requestBody = RequestBody.GET_USER.name
            // without any interceptors, proceed should throw exception
            InterceptorChain(Request(requestBody)).proceed()
        }
    }

    /**
     * Verifies that InterceptorChain throws Exception when supplied all
     * interceptors but ServerCallInterceptor which actually returns response.
     */
    @Test
    fun proceed_noResponseInterceptor_throwsException() {
        assertThrows<Exception>("Reached end-of-chain! Response responsibility interceptor not found.") {
            val requestBody = RequestBody.GET_USER.name
            // without response interceptor, proceed should throw exception
            InterceptorChain(Request(requestBody))
                .addInterceptor(LoggingInterceptor)
                .addInterceptor(EncodeRequestBodyInterceptor)
                .addInterceptor(AuthorizationHeaderInterceptor)
                .addInterceptor(DecodeResponseBodyInterceptor)
                .proceed()
        }
    }

    /**
     * Verifies that the InterceptorChain throws exception if there are
     * no Request-Encoding and Response-Decoding Interceptors and only
     * ServerCallInterceptor is present.
     */
    @Test
    fun proceed_onlyResponseInterceptor_throwsException() {
        assertThrows<Exception> {
            val requestBody = RequestBody.GET_USER.name
            InterceptorChain(Request(requestBody))
                // requires request-encoder and response-decoder
                .addInterceptor(ServerCallInterceptor)
                .proceed()
        }
    }

    /**
     * Verifies that the InterceptorChain works successfully but returns invalid
     * response when ServerCallInterceptor is placed in between other Interceptors.
     */
    @Test
    fun proceed_responseInterceptorBetweenInterceptors_returnsInvalidResponse() {
        val getUserRequestBody = RequestBody.GET_USER.name
        InterceptorChain(Request(getUserRequestBody))
            .addInterceptor(LoggingInterceptor)
            .addInterceptor(EncodeRequestBodyInterceptor)
            .addInterceptor(AuthorizationHeaderInterceptor)
            // response interceptor should always follow the rest
            .addInterceptor(ServerCallInterceptor)
            .addInterceptor(DecodeResponseBodyInterceptor)
            .proceed().run {
                // chain will still return response
                assertNotNull(this)
                assertNotNull(body)
                // but the response will be malformed
                assertNotEquals(WebApiServer.RESPONSE_GET_USER, body)
            }
    }

    /**
     * Verifies that the InterceptorChain works successfully when there are
     * all Interceptors along with ServerCallInterceptor.
     */
    @Test
    fun proceed_allInterceptors_returnsSuccess() {

        /** executes chain with given request-body, and performs assertion with expectedResponseBody */
        fun executeAndAssertChain(requestBody: String, expectedResponseBody: String) {

            InterceptorChain(Request(requestBody))
                .addInterceptor(LoggingInterceptor)
                .addInterceptor(EncodeRequestBodyInterceptor)
                .addInterceptor(DecodeResponseBodyInterceptor)
                .addInterceptor(AuthorizationHeaderInterceptor)
                // response interceptor should always follow the rest
                .addInterceptor(ServerCallInterceptor)
                .proceed().run {
                    assertNotNull(this)
                    // chain should return valid response
                    assertEquals(expectedResponseBody, body)
                }
        }

        // execute and assert get-user-request
        val getUserRequestBody = RequestBody.GET_USER.name
        executeAndAssertChain(getUserRequestBody, WebApiServer.RESPONSE_GET_USER)

        // execute and assert post-user-request
        val postUserRequestBody = RequestBody.POST_USER.name
        executeAndAssertChain(postUserRequestBody, WebApiServer.RESPONSE_POST_USER)

        // execute and assert random request
        val anonymousRequestBody = "anonymous"
        executeAndAssertChain(anonymousRequestBody, WebApiServer.RESPONSE_UNKNOWN)
    }
}