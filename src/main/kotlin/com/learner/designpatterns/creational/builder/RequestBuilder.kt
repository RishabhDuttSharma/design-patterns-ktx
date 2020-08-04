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

package com.learner.designpatterns.creational.builder

/**
 *
 * Created by Rishabh on 02-08-2020
 */
class Request {
    val headers by lazy { mutableMapOf<String, String>() }
    val queryParams by lazy { mutableMapOf<String, String>() }
    var body: String? = null
}

interface RequestBuilder {

    fun addHeader(header: String, value: String)

    fun addQueryParam(query: String, value: String)

    fun setBody(body: String)

    fun build(): Request
}

class GetRequestBuilder : RequestBuilder {

    private val request = Request()

    override fun addHeader(header: String, value: String) {
        request.headers[header] = value
    }

    override fun setBody(body: String) {
        throw Exception("GET-Request cannot have body")
    }

    override fun addQueryParam(query: String, value: String) {
        request.queryParams[query] = value
    }

    override fun build(): Request {
        return request
    }
}

class PostRequestBuilder : RequestBuilder {

    private val request = Request()

    override fun addHeader(header: String, value: String) {
        request.headers[header] = value
    }

    override fun setBody(body: String) {
        request.body = body
    }

    override fun addQueryParam(query: String, value: String) {
        request.queryParams[query] = value
    }

    override fun build(): Request {
        return request
    }
}