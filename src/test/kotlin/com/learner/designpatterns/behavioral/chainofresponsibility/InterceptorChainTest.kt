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

import org.junit.Assert
import org.junit.Test

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
        try {
            InterceptorChain(Request("Sample")).proceed()
        } catch (ex: Exception) {
            Assert.assertNotNull(ex)
            Assert.assertEquals(ex.message, "Reached end-of-chain! Response responsibility interceptor not found.")
        }
    }

    /**
     * Verifies that InterceptorChain throws Exception when supplied all
     * interceptors but ServerCallInterceptor which actually returns response.
     */
    @Test
    fun proceed_noResponseInterceptor_throwsException() {
        try {
            InterceptorChain(Request("Sample"))
                .addInterceptor(EncodeRequestBodyInterceptor)
                .addInterceptor(AuthorizationHeaderInterceptor)
                .addInterceptor(DecodeResponseBodyInterceptor)
                .proceed()
        } catch (ex: Exception) {
            Assert.assertNotNull(ex)
        }
    }

    /**
     * Verifies that the InterceptorChain works successfully when there are
     * all Interceptors along with ServerCallInterceptor.
     */
    @Test
    fun proceed_allInterceptors_returnsSuccess() {
        try {
            val callBody = "Sample"
            InterceptorChain(Request(callBody))
                .addInterceptor(EncodeRequestBodyInterceptor)
                .addInterceptor(AuthorizationHeaderInterceptor)
                .addInterceptor(DecodeResponseBodyInterceptor)
                .addInterceptor(ServerCallInterceptor)
                .proceed().run {
                    Assert.assertNotNull(this)
                    Assert.assertEquals(callBody, body)
                }
        } catch (ex: Exception) {
            Assert.assertNull(ex)
        }
    }

    /**
     * Verifies that the InterceptorChain works successfully even if there are
     * no Interceptors except ServerCallInterceptor which actually returns response.
     */
    @Test
    fun proceed_responseInterceptor_returnsSuccess() {
        try {
            val callBody = "Sample"
            InterceptorChain(Request(callBody))
                .addInterceptor(ServerCallInterceptor)
                .proceed().run {
                    Assert.assertNotNull(this)
                    Assert.assertEquals(callBody, body)
                }
        } catch (ex: Exception) {
            Assert.assertNull(ex)
        }
    }
}