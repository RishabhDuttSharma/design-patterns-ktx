package com.learner.designpatterns.creational.singleton

import org.junit.Assert
import org.junit.Test


internal class LazyInitializationSingletonTest {

    @Test
    fun test_getInstance() {
        val instance = LazyInitializationSingleton.getInstance()
        Assert.assertNotNull(instance)
        Assert.assertEquals(instance, LazyInitializationSingleton.getInstance())
    }
}