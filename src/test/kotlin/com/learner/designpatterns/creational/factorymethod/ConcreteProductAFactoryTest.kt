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

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Test Cases for [ConcreteProductAFactory]
 *
 * Created by Rishabh on 24-05-2020
 */
internal class ConcreteProductAFactoryTest {

    /**
     * Verifies that the Factory creates a non-null instance of Product
     */
    @Test
    fun verify_createProduct_returnsProduct() {
        Assertions.assertNotNull(ConcreteProductAFactory().createProduct())
    }

    /**
     * Verifies that the Factories always creates a new Product
     */
    @Test
    fun verify_createProduct_alwaysReturnNewProduct() {
        val productA1 = ConcreteProductAFactory().createProduct()
        val productA2 = ConcreteProductAFactory().createProduct()
        Assertions.assertNotEquals(productA1, productA2)

        val productA3 = ConcreteProductAFactory().createProduct()
        Assertions.assertNotEquals(productA2, productA3)
    }
}