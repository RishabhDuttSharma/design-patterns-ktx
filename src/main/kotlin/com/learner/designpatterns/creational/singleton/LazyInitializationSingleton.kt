package com.learner.designpatterns.creational.singleton

class LazyInitializationSingleton private constructor() {

    companion object {

        class InstanceHolder {
            companion object {
                internal val instance = LazyInitializationSingleton()
            }
        }

        fun getInstance(): LazyInitializationSingleton = InstanceHolder.instance
    }
}