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
 *
 * Created by Rishabh on 21-05-2020
 */
internal class LazyInitializationSingletonTest {

    @Test
    fun test_getInstance() {
        val instance = LazyInitializationSingleton.getInstance()
        Assert.assertNotNull(instance)
        Assert.assertEquals(instance, LazyInitializationSingleton.getInstance())
    }
}