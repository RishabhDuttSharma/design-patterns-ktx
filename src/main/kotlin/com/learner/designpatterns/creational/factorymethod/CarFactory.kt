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

/** Created by Rishabh on 24-05-2020 */

/**
 * Defines an abstract layer for Factories that create specific Product.
 */
interface IFactory {

    /**
     * Factory method that actually creates Product
     */
    fun createProduct(): IProduct
}


/**
 * Defines an abstraction for underlying class of Products.
 */
interface IProduct


/**
 * Concrete implementation of IProduct, features type "A".
 */
class ConcreteProductA : IProduct


/**
 * Concrete implementation of IFactory that deals with creation of ProductA.
 *
 * If Factories are just concerned with creating a new Product, they should be
 * implemented as singleton. For Kotlin, we can implement below factory as object,
 * instead of a class, so that we don't instantiate it over and again.
 */
class ConcreteProductAFactory : IFactory {

    override fun createProduct() = ConcreteProductA()
}

/** playground */
fun main() {
    ConcreteProductAFactory().createProduct()
}
