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
 * Way to implement Singleton, on-demand, if target class constructor doesn't take
 * any parameters (i.e., a default constructor)
 *
 * It differs from early-initialization-singleton in the approach used in initialization.
 * Unlike early-initialization-singleton, it doesn't create the instance on any call.
 * It uses a instance-holder (or singleton-holder) to wrap the instance initialization
 * so that the instance is created only on the first call to instance-holder. Only
 * getInstance() method should call the instance-holder.
 *
 * Since this approach creates the singleton only on demand,
 * it is called lazy-initialization-singleton.
 *
 * Created by Rishabh on 21-05-2020
 */
class LazyInitializationSingleton private constructor() {

    companion object {

        /**
         * Wraps the single-instance and its initialization
         */
        private class InstanceHolder {
            companion object {
                internal val instance = LazyInitializationSingleton()
            }
        }

        /**
         * Acts as a getter (and only way) to access the single-instance.
         *
         * An instance is created on first call to InstanceHolder, and returned
         * the same instance on successive calls.
         */
        fun getInstance(): LazyInitializationSingleton = InstanceHolder.instance
    }
}