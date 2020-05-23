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
 * Way to implement singleton, if target class constructor doesn't
 * take any parameter(s) (i.e., a default constructor)
 *
 * The instance will be initialized upon any first call to this Class.
 * Being inside a [Companion] (or Java static), will create an instance
 * as soon as any reference is made to this Class.
 *
 * Created by Rishabh on 21-05-2020
 */
class EarlyInitializationSingleton private constructor() {

    companion object {

        private val instance = EarlyInitializationSingleton()

        fun getInstance() = instance
    }
}