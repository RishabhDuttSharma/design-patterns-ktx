package com.learner.designpatterns.creational.singleton

import org.junit.Assert
import org.junit.Test


internal class EarlyInitializationSingletonTest {

    @Test
    fun test_getInstance() {
        val instance = EarlyInitializationSingleton.getInstance()
        Assert.assertNotNull(instance)
        Assert.assertEquals(instance, EarlyInitializationSingleton.getInstance())
    }
}