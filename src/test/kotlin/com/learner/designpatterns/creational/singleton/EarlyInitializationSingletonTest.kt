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

package com.learner.designpatterns.creational.singleton

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test


/**
 * Test Cases for [EarlyInitializationSingleton]
 *
 * Created by Rishabh on 21-05-2020
 */
internal class EarlyInitializationSingletonTest {

    private lateinit var mInstance: EarlyInitializationSingleton

    /**
     * Verifies that getInstance() method always returns same instance
     */
    @Test
    fun test_getInstance_alwaysReturnsSameInstance() {
        val instance = EarlyInitializationSingleton.getInstance()
        assertNotNull(instance)
        assertEquals(instance, EarlyInitializationSingleton.getInstance())

        mInstance = instance
    }

    /**
     * Verifies that the instance is created even if we don't directly touch the Class
     */
    @Test
    fun test_callClassForName_verifyInstanceVal() {
        // retrieve the instance property using Class.forName() + Reflection
        val instanceVal = Class.forName("com.learner.designpatterns.creational.singleton.EarlyInitializationSingleton")
            // by now instance will be created
            .getDeclaredField("instance").apply {
                isAccessible = true
            }.get(null)

        // the instance should be initialized as soon as Class.forName reaches given Class
        assertNotNull(instanceVal)
    }

    /**
     * Verifies that the instance is created even by just referring to Class in any way
     * (e.g., any property or method (apart from getInstance))
     */
    @Test
    fun test_callReflection_verifyInstanceVal() {
        // retrieve the instance property using Reflection
        val instanceVal = EarlyInitializationSingleton::class.java
            // by now instance will be created
            .getDeclaredField("instance").apply {
                isAccessible = true
            }.get(null)

        // the instance should be initialized just before EarlyInitializationSingleton::class.java returns
        assertNotNull(instanceVal)
    }
}