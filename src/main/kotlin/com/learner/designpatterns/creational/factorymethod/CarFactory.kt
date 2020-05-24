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

package com.learner.designpatterns.creational.factorymethod

/**
 *
 * Created by Rishabh on 24-05-2020
 */

const val CAR_TYPE_SEDAN = "sedan"

interface ICarFactory {
    fun manufactureCar(): ICar
}

interface ICar {
    fun getType(): String
}

class SedanCar : ICar {
    override fun getType() = CAR_TYPE_SEDAN
}

class SedanCarFactory : ICarFactory {
    override fun manufactureCar() = SedanCar()
}

fun main() {
    SedanCarFactory().manufactureCar()
}
