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
 * Chain of Interceptors
 * ----------------------
 *
 * API Calls to Server involve a Request for processing, and as a result return
 * corresponding Response. While making API Calls, it might be required to modify
 * the contents of the raw Request to be acceptable by the Server. Likewise,
 * the Response may also require modification in order to be parsed at Client-Site.
 *
 * To achieve above system, we have following conclusions:-
 *
 * 1. Changing the contents of Call at a given call-site will require
 * implementing the same at every other call-site.
 *
 * 2. Implementing a single class for changing the contents of the Call violates
 * Single-Responsibility-Principle (as it might perform request-encoding,
 * response-decoding, response-parsing, etc.).
 *
 * 3. Recommended approach is to modify the contents of the Call at different levels.
 * Since each level intercepts the Call (either for purpose of monitoring, logging,
 * modification, etc.), they are called Interceptors. The last interceptor is actually
 * responsible for making Call to the Server, and return the Response.
 *
 * Interceptors are managed into a Chain, and intercept the Calls in order of their
 * insertion into the Chain. Since each interceptor in the chain has some form of
 * responsibility over the Call, this approach is called Chain of Responsibility.
 *
 *
 * Created by Rishabh on 24-05-2020
 */

/**
 * Represents a request-entity in the System.
 * Encapsulates request-related properties.
 */
data class Request(
    val body: String,
    val headers: MutableMap<String, String> = mutableMapOf()
)

/**
 * Represents a response-entity in the system.
 * Encapsulates response-related properties.
 */
class Response(val body: String)

/**
 * Interceptors monitor and re-write calls that involve returning
 * a response w.r.t. a request.
 */
interface Interceptor {

    /**
     * Intercepts a Call wrapped inside the chain, to perform monitoring
     * or modification.
     *
     * To continue further execution after request-related changes, it should
     * invoke [chain.proceed()] unless it has the responsibility to return the
     * response. After [chain.proceed()] has returned response, further changes
     * can be made to response, and eventually return the same.
     *
     */
    fun intercept(chain: Chain): Response
}

/**
 * Abstraction layer for a chain of calls through which a request flows up to
 * Server, and a corresponding response flows back to the call-site.
 */
abstract class Chain(var request: Request) {

    /**
     * Invokes the chain to proceed to next interceptor in the Chain.
     *
     * Interceptors should work independently, without having the knowledge about
     * other interceptors in the Chains. Thus, it enables separation-of-concern.
     *
     * @return the response of execution.
     */
    @Throws(Exception::class)
    abstract fun proceed(): Response
}

/**
 * Queue based implementation for managing the [Chain] of [Interceptor]s.
 */
class InterceptorChain(request: Request) : Chain(request) {

    private val interceptorQueue = LinkedBlockingQueue<Interceptor>()

    override fun proceed() = interceptorQueue.poll()?.intercept(this)
        ?: throw Exception("Reached end-of-chain! Response responsibility interceptor not found.")

    /**
     * Adds an [Interceptor] to the Chain
     */
    fun addInterceptor(interceptor: Interceptor) = apply {
        interceptorQueue.offer(interceptor)
    }
}

/**
 * Logs the request-body and response-body
 */
object LoggingInterceptor : Interceptor {

    override fun intercept(chain: Chain) = chain.also {
        println("Request-Body : ${chain.request.body}")
    }.proceed().also {
        println("Response-Body : ${it.body}")
    }
}

/**
 * Encodes the request-body, so that it can only be read by target Server
 */
object EncodeRequestBodyInterceptor : Interceptor {

    override fun intercept(chain: Chain) = chain.apply {
        // since request-body is immutable, re-create Request with new body
        request = Request(EncoderDecoder.encode(request.body))
    }.proceed()
}

/**
 * Adds the authorization headers to the existing request
 */
object AuthorizationHeaderInterceptor : Interceptor {

    override fun intercept(chain: Chain) = chain.apply {
        request.headers["authorization"] = "access_token"
    }.proceed()
}

/**
 * Decodes the encoded-response from Sever
 */
object DecodeResponseBodyInterceptor : Interceptor {

    override fun intercept(chain: Chain) = chain.proceed().run {
        EncoderDecoder.decode(body)
    }.let(::Response)
}

/**
 * Actual interceptor that makes call to Server, and returns response
 */
object ServerCallInterceptor : Interceptor {

    override fun intercept(chain: Chain) = chain.request.run {
        WebApiServer.processRequest(body)
    }.let(::Response)
}

fun main() {

    // create-request-body
    val requestBody = RequestBody.values().random().name

    // create request and pass it to interceptor-chain
    InterceptorChain(Request(requestBody))
        .addInterceptor(LoggingInterceptor)
        .addInterceptor(EncodeRequestBodyInterceptor)
        .addInterceptor(AuthorizationHeaderInterceptor)
        .addInterceptor(DecodeResponseBodyInterceptor)
        .addInterceptor(ServerCallInterceptor)
        .proceed()
}

/*====================== helper code ==========================*/

/**
 * Dummy WebServer that processes requests and returns response
 */
object WebApiServer {

    const val RESPONSE_GET_USER = "Rishabh"
    const val RESPONSE_POST_USER = "Success"
    const val RESPONSE_UNKNOWN = "Unknown"

    /**
     * Processes requests and returns response
     */
    fun processRequest(encodedRequestBody: String): String =
        EncoderDecoder.decode(encodedRequestBody).let { requestBody ->
            try {
                RequestBody.valueOf(requestBody)
            } catch (ex: Exception) {
                null
            }
        }.let {
            when (it) {
                RequestBody.GET_USER -> RESPONSE_GET_USER
                RequestBody.POST_USER -> RESPONSE_POST_USER
                else -> RESPONSE_UNKNOWN
            }
        }.let(EncoderDecoder::encode)
}

/**
 * Enumeration to wrap request-body-types
 */
enum class RequestBody {
    GET_USER, POST_USER
}

/**
 * Singleton to handle encoding and decoding of data
 */
object EncoderDecoder {

    /**
     * Encodes input, and returns it
     *
     * @return encoded form of input
     */
    fun encode(input: String): String = Base64.getEncoder().encodeToString(input.toByteArray())

    /**
     * Decodes input, and returns it
     *
     * @return decoded form of input
     */
    fun decode(input: String): String = String(Base64.getDecoder().decode(input))
}