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

package com.learner.designpatterns.structural.decorator

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Test Cases for [PizzaDecorator]
 *
 * Created by Rishabh on 30-05-2020
 */
internal class PizzaDecoratorTest {

    private val margheritaPizza = MargheritaPizza // +200
    private val farmhousePizza = FarmhousePizza // +300
    private val mexicanGreenWavePizza = MexicanGreenWavePizza // +400

    @Test
    fun getCost_onePizzaOneDecorator() {

        /** Retrieves the cost of pizza wrapped by [ExtraCheeseDecorator] */
        fun getExtraCheeseDecoratedCost(pizza: IPizza) = ExtraCheeseDecorator(pizza).getCost() // +80

        Assertions.assertEquals(280, getExtraCheeseDecoratedCost(margheritaPizza))
        Assertions.assertEquals(380, getExtraCheeseDecoratedCost(farmhousePizza))
        Assertions.assertEquals(480, getExtraCheeseDecoratedCost(mexicanGreenWavePizza))

        /** Retrieves the cost of pizza wrapped by [ExtraVeggiesDecorator] */
        fun getExtraVeggiesDecoratedCost(pizza: IPizza) = ExtraVeggiesDecorator(pizza).getCost() // +120

        Assertions.assertEquals(320, getExtraVeggiesDecoratedCost(margheritaPizza))
        Assertions.assertEquals(420, getExtraVeggiesDecoratedCost(farmhousePizza))
        Assertions.assertEquals(520, getExtraVeggiesDecoratedCost(mexicanGreenWavePizza))
    }

    @Test
    fun getCost_onePizzaMultipleDecorator() {

        margheritaPizza.let { pizza ->
            ExtraCheeseDecorator(pizza) // +80
        }.let { cheeseDecoratedPizza ->
            ExtraVeggiesDecorator(cheeseDecoratedPizza) // +120
        }.let { veggiesCheeseDecoratedPizza ->
            Assertions.assertEquals(400, veggiesCheeseDecoratedPizza.getCost())
        }

        farmhousePizza.let { pizza ->
            ExtraVeggiesDecorator(pizza) // +120
        }.let { veggiesDecoratedPizza ->
            ExtraVeggiesDecorator(veggiesDecoratedPizza) // +120
        }.let { veggiesVeggiesDecoratedPizza ->
            ExtraCheeseDecorator(veggiesVeggiesDecoratedPizza) // +80
        }.let { cheeseVeggiesVeggiesDecoratedPizza ->
            Assertions.assertEquals(620, cheeseVeggiesVeggiesDecoratedPizza.getCost())
        }
    }
}