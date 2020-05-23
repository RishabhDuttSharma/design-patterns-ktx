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

/**
 * Way to implement singleton in a thread-safe manner.
 *
 * It involves checking the instance for nullability twice before creating
 * a new instance.
 *
 * First check determines whether there is a need to create the instance, else
 * return it. The other check is placed inside synchronized block. It ensures
 * that the required instance was't created by another thread while the current thread
 * was waiting on the synchronized block.
 *
 * Created by Rishabh on 21-05-2020
 */
class DoubleCheckedLockingSingleton private constructor() {

    companion object {

        private var instance: DoubleCheckedLockingSingleton? = null

        /**
         * @return a new instance of the class, if not available, else return it
         */
        fun getInstance() = instance ?: synchronized(DoubleCheckedLockingSingleton::class.java) {
            instance ?: DoubleCheckedLockingSingleton().also { instance = it }
        }
    }
}