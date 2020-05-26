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

import java.util.*
import java.util.concurrent.LinkedBlockingQueue

/**
 *
 * Created by Rishabh on 24-05-2020
 */

data class Request(
    val body: String,
    val headers: MutableMap<String, String> = mutableMapOf()
)

class Response(val body: String)

interface Interceptor {
    fun intercept(chain: Chain): Response
}

abstract class Chain(var request: Request) {
    abstract fun proceed(): Response
}

class InterceptorChain(request: Request) : Chain(request) {

    private val interceptorQueue = LinkedBlockingQueue<Interceptor>()

    override fun proceed() = interceptorQueue.poll().intercept(this)

    fun addInterceptor(interceptor: Interceptor) = apply {
        interceptorQueue.add(interceptor)
    }
}

object EncodeRequestBodyInterceptor : Interceptor {
    override fun intercept(chain: Chain) = chain.apply {
        val arrRequestBody = request.body.toByteArray()
        val newRequestBody = Base64.getEncoder().encodeToString(arrRequestBody)
        request = Request(newRequestBody)
    }.proceed()
}

object AuthorizationHeaderInterceptor : Interceptor {
    override fun intercept(chain: Chain) = chain.apply {
        request.headers["authorization"] = "access_token"
    }.proceed()
}

object DecodeResponseBodyInterceptor : Interceptor {
    override fun intercept(chain: Chain) = chain.proceed().run {
        val arrResponseBody = body.toByteArray()
        val arrDecodedBody = Base64.getDecoder().decode(arrResponseBody)
        Response(String(arrDecodedBody))
    }
}

object ServerCallInterceptor : Interceptor {
    override fun intercept(chain: Chain) = chain.request.run {
        Response(body)
    }
}

fun main() {
    InterceptorChain(Request("Sample"))
        .addInterceptor(EncodeRequestBodyInterceptor)
        .addInterceptor(AuthorizationHeaderInterceptor)
        .addInterceptor(DecodeResponseBodyInterceptor)
        .addInterceptor(ServerCallInterceptor)
        .proceed().run {
            println(body)
        }
}