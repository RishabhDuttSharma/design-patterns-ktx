package com.learner.designpatterns.creational.singleton

class EarlyInitializationSingleton private constructor() {

    companion object {

        private val instance = EarlyInitializationSingleton()

        fun getInstance() = instance
    }
}