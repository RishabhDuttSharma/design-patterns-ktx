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

package com.learner.designpatterns.behavioral.templatemethod

import java.io.FileReader

/**
 *
 * Created by Rishabh on 21-06-2020
 */

data class CustomerInfo(
    val personalInfo: PersonalInfo,
    val contactInfo: ContactInfo,
    val addressInfo: AddressInfo
)

data class PersonalInfo(
    val name: String,
    val dob: String
)

data class AddressInfo(
    val address: String,
    val pinCode: String
)

data class ContactInfo(
    val email: String,
    val phone: String
)

interface CustomerInfoTemplate {

    fun createCustomerInfo(fileName: String): CustomerInfo = readDataFromFile(fileName).let {
        return CustomerInfo(
            extractPersonalInfo(it),
            extractContactInfo(it),
            extractAddressInfo(it)
        )
    }

    fun readDataFromFile(fileName: String): String = FileReader(fileName).readText()

    fun extractPersonalInfo(data: String): PersonalInfo
    fun extractContactInfo(parsedInfo: String): ContactInfo
    fun extractAddressInfo(parsedInfo: String): AddressInfo
}

class CustomerInfoXmlTemplate : CustomerInfoTemplate {

    override fun extractPersonalInfo(data: String): PersonalInfo {
        TODO("Not yet implemented")
    }

    override fun extractContactInfo(parsedInfo: String): ContactInfo {
        TODO("Not yet implemented")
    }

    override fun extractAddressInfo(parsedInfo: String): AddressInfo {
        TODO("Not yet implemented")
    }

}

class CustomerInfoJsonTemplate : CustomerInfoTemplate {

    override fun extractPersonalInfo(data: String): PersonalInfo {
        TODO("Not yet implemented")
    }

    override fun extractContactInfo(parsedInfo: String): ContactInfo {
        TODO("Not yet implemented")
    }

    override fun extractAddressInfo(parsedInfo: String): AddressInfo {
        TODO("Not yet implemented")
    }

}