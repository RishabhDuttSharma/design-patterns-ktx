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

import org.junit.Assert
import org.junit.Test


/**
 * Test Cases for [DoubleCheckedLockingSingleton]
 *
 * Created by Rishabh on 21-05-2020
 */
internal class DoubleCheckedLockingSingletonTest {

    /**
     * Verifies that getInstance() method always returns same instance
     */
    @Test
    fun test_getInstance_alwaysReturnsSameInstance() {
        val instance = DoubleCheckedLockingSingleton.getInstance()
        Assert.assertNotNull(instance)
        Assert.assertEquals(instance, DoubleCheckedLockingSingleton.getInstance())
    }
}