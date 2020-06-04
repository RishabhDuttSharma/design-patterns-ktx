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
 * Cross Platform GUI
 * -------------------
 *
 * A cross-platform gui library may have support for several platforms.
 * For each GUI component, it would have a specific implementation for
 * each of the platforms.
 *
 * A Button, for instance, would have two different implementations, each
 * for Windows and MacOS, assuming the library supports both of them.
 * To maintain the contract, an abstract layer for Button would be required
 * for the concrete implementations.
 *
 * Further, for a Dialog that consists of a "ACTION" Button, would also have
 * two different implementations. Again, to maintain the contract, the
 * Dialog would have also have a layer of abstraction. Since the Dialog
 * cannot predict which implementation of Button would be required, thus
 * it would leave the task of creating Button to sub-classes. Thus, the
 * Dialog abstraction-layer would have an abstract-method for the creation
 * of Button, which is left for the sub-classes to implement. This abstract
 * method is called FACTORY-METHOD.
 *
 * As a result, WindowsDialog will create a WindowsButton and MacOsDialog will
 * create a MacOsButton, using their factory-method.
 *
 *
 * Created by Rishabh on 29-05-2020
 */


/**
 * Abstraction layer for the Button
 * Defines the contract for Button implementations
 */
interface IButton {

    /**
     * Draws the Button
     */
    fun draw()
}


/**
 * Concrete implementation of Button for MacOS
 */
class MacOsButton : IButton {

    override fun draw() = print("MacOS -> Drawing Button using XCode Tools...")
}

/**
 * Concrete implementation of Button for Windows
 */
class WindowsButton : IButton {

    override fun draw() = print("Windows -> Drawing Button using VisualStudio Tools...")
}

/**
 * Abstraction layer for a Dialog that consists of a Button
 * Defines the contract for the Dialog implementations
 */
abstract class IDialog {

    /**
     * Renders the Dialog, and its components
     */
    fun render() {
        // create Button using factory-method
        createButton().also { actionButton ->
            // draw the button on screen
            actionButton.draw()
        }
    }

    /**
     * Factory-Method for creating platform specific Button
     *
     * NOTE: This method must be implemented by sub-classes to create
     * and return an appropriate instance of [IButton]
     *
     * @return instance of [IButton]
     */
    protected abstract fun createButton(): IButton
}

/**
 * Concrete implementation of Dialog for Windows
 */
class WindowsDialog : IDialog() {

    /** Factory-method: Creates WindowsButton */
    override fun createButton(): IButton = WindowsButton()
}


/**
 * Concrete implementation of Dialog for MacOs
 */
class MacOsDialog : IDialog() {

    /** Factory-method: Creates MacOsButton */
    override fun createButton(): IButton = MacOsButton()
}

/** playground */
fun main() {
    // evaluate the target-platform
    when (Platform.values().random()) {
        // create dialog based on the platform
        Platform.WINDOWS -> WindowsDialog()
        Platform.MAC_OS -> MacOsDialog()
    }.render()  // show dialog
}

/**
 * Enumeration to wrap the Platforms supported by GUI-Library
 */
enum class Platform {
    WINDOWS, MAC_OS
}