package com.learner.designpatterns.creational.singleton

import org.junit.Assert
import org.junit.Test


internal class DoubleCheckedLockingSingletonTest {

    @Test
    fun test_getInstance() {
        val instance = DoubleCheckedLockingSingleton.getInstance()
        Assert.assertEquals(instance, DoubleCheckedLockingSingleton.getInstance())
    }
}