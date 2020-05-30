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

/**
 * Pizza Decorator
 * ----------------
 *
 * Pizza comes in various types such as Margherita, Farmhouse, MexicanGreenWave, etc.
 * and they have respective base-prices.
 *
 * In addition to these types, they have several customization-options.
 * Customization-options may include Extra-Cheese-Topping, Extra-Veggies-Topping, etc.
 * These options have an associated charge that eventually becomes part of
 * the total-amount.
 *
 * Problem: To calculate the total amount after customization
 * -----------------------------------------------------------
 *
 * A layer of abstraction is required to define a contract for different Pizzas.
 * This layer abstracts the cost of a given pizza. All the different pizzas implement
 * this abstraction along with their specific cost.
 *
 * To accommodate customization-options, we create another abstraction layer that
 * wraps pizza-type, and also implements the Pizza abstraction layer (as ultimately
 * it will also become pizza. e.g., putting extra cheese on pizza creates a new Pizza).
 * This wrapper calculates its cost as a sum of both its-own-cost and wrapped-pizza-cost.
 *
 * Since this approach decorates an existing type by adding customizations to it
 * at runtime, it is known as Decorator Pattern.
 *
 *
 * Created by Rishabh on 30-05-2020
 */


/**
 * Abstraction layer for Pizza
 * Defines a contract for implementing concrete Pizza
 */
interface IPizza {

    /**
     * @return name of the Pizza
     */
    fun getName(): String

    /**
     * Calculates and returns the cost of the Pizza
     *
     * @return the total cost of the Pizza
     */
    fun getCost(): Int
}

/**
 * Concrete Pizza implementation for type - Margherita
 */
object MargheritaPizza : IPizza {

    override fun getName() = "Margherita"

    override fun getCost() = 200
}

/**
 * Concrete Pizza implementation for type - Farmhouse
 */
object FarmhousePizza : IPizza {

    override fun getName() = "Farmhouse"

    override fun getCost() = 300
}

/**
 * Concrete Pizza implementation for type - Mexican Green Wave
 */
object MexicanGreenWavePizza : IPizza {

    override fun getName() = "Mexican Green Wave"

    override fun getCost() = 400
}

/**
 * Abstraction layer for Pizza Decorators.
 * Wraps a Pizza implementation, and also implements Pizza
 */
abstract class PizzaDecorator(private val pizza: IPizza) : IPizza {

    override fun getName(): String = "${pizza.getName()} (${getDecoratorName()})"

    override fun getCost() = pizza.getCost() + getDecoratorCost()

    /**
     * @return the individual cost of decorator
     */
    protected abstract fun getDecoratorCost(): Int

    /**
     * @return the name of decorator
     */
    protected abstract fun getDecoratorName(): String
}

/**
 * Decorator represents Extra-Cheese Topping
 */
class ExtraCheeseDecorator(pizza: IPizza) : PizzaDecorator(pizza) {

    override fun getDecoratorName() = "Extra Cheese"

    override fun getDecoratorCost() = 80
}

/**
 * Decorator represents Extra-Veggies Topping
 */
class ExtraVeggiesDecorator(pizza: IPizza) : PizzaDecorator(pizza) {

    override fun getDecoratorName() = "Extra Veggies"

    override fun getDecoratorCost() = 120
}


/** playground */
fun main() {

    // choose a random pizza-type
    when (PizzaType.values().random()) {
        // create a new instance of pizza
        PizzaType.MARGHERITA -> MargheritaPizza
        PizzaType.FARMHOUSE -> FarmhousePizza
        PizzaType.MEXICAN_GREEN_WAVE -> MexicanGreenWavePizza
    }.let { pizza ->
        // choose a random customization-type
        when (CustomizationType.values().random()) {
            // wrap pizza in customization-decorator
            CustomizationType.EXTRA_CHEESE_TOPPING -> ExtraCheeseDecorator(pizza)
            CustomizationType.EXTRA_VEGGIES_TOPPING -> ExtraVeggiesDecorator(pizza)
        }
    }.let { decoratedPizza ->
        // display specs of decorated-pizza
        println("Items: ${decoratedPizza.getName()}")
        println("Total Cost: ${decoratedPizza.getCost()}")
    }
}

/**
 * Enumeration to wrap available Pizza types
 */
enum class PizzaType {
    MARGHERITA, FARMHOUSE, MEXICAN_GREEN_WAVE
}

/**
 *  Enumeration to wrap available Customization types
 */
enum class CustomizationType {
    EXTRA_CHEESE_TOPPING, EXTRA_VEGGIES_TOPPING
}