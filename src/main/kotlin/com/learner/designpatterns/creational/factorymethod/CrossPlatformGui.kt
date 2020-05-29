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

/**
 *
 * Created by Rishabh on 29-05-2020
 */

// Product
interface IButton {

    fun draw()

    fun onClick()
}


// Concrete Product - MacOS
class MacOsButton : IButton {

    override fun draw() {
        println("MacOS -> Drawing Button using XCode Tools...")
    }

    override fun onClick() {
        println("Opening Finder...")
    }
}

// Concrete Product - Windows
class WindowsButton : IButton {

    override fun draw() {
        println("Windows -> Drawing Button using VisualStudio Tools...")
    }

    override fun onClick() {
        println("Opening Explorer...")
    }
}

// Factory
interface IDialog {

    fun renderDialog() {
        // create a new button (using factory-method)
        val button = createButton()
        // draw the button on screen
        button.draw()
        // use when required
        button.onClick()
    }

    // Factory-method
    fun createButton(): IButton
}

class WindowsDialog : IDialog {
    override fun createButton(): IButton = WindowsButton()
}

class MacOsDialog : IDialog {
    override fun createButton(): IButton = MacOsButton()
}

fun main() {
    WindowsDialog().renderDialog()
    MacOsDialog().renderDialog()
}
