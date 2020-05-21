package com.learner.designpatterns.creational.singleton

class DoubleCheckedLockingSingleton private constructor() {

    companion object {

        private var instance: DoubleCheckedLockingSingleton? = null

        fun getInstance() = instance ?: synchronized(DoubleCheckedLockingSingleton::class.java) {
            instance ?: DoubleCheckedLockingSingleton().also { instance = it }
        }
    }
}