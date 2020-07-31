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

package com.learner.designpatterns.creational.abstractfactory

/**
 * System-Dialog-Factory
 * ---------------------
 *
 * A system-framework requires to show short informative messages in form of
 * Dialogs, for an event. There may be several types of dialogs (e.g., simple-
 * message-dialog, action-dialog, etc.) which have different implementations
 * for various systems (e.g., Windows, MacOS, etc.) that are supported by the framework.
 *
 * To design such a requirement it would be required to have an abstract-factory-class
 * that would be implemented for each of the supported system. This abstract-factory-class
 * will have abstract factory-methods for creating each type of Dialog.
 *
 * Abstract-factory-class is represented by [SystemDialogFactory], and implemented by
 * [WindowsDialogFactory] and [MacOsDialogFactory]. System-dialogs are represented by
 * [SystemDialog] implementations e.g,. [SimpleDialog] and [ActionDialog].
 *
 * Created by Rishabh on 26-07-2020
 *
 */
object FileDocumentation

/**
 * Enumeration that wraps dialog-configurations for different systems.
 */
enum class DialogConfig(val systemType: String) {
    Windows("Windows-Dialog"),
    MacOs("MacOs-Dialog")
}

/**
 * Represents the abstraction-layer for implementing a dialog for an underlying-system.
 * {abstract-product}
 *
 * @param dialogConfig the configuration of the underlying-system
 */
abstract class SystemDialog(private val dialogConfig: DialogConfig) {

    /**
     * Shows the contents specific to a System-Dialog.
     * It should be overridden by child-classes to show their contents.
     */
    protected abstract fun showContents()

    /**
     * Displays dialog on the Screen according to the provided [DialogConfig].
     * It internally calls the [showContents] to show implementation-specific contents.
     */
    fun show() {
        println("=== ${dialogConfig.systemType} ===")
        showContents()
        println("=== ENDS ===")
    }
}

/**
 * [SystemDialog] that shows an information-message on the screen
 * {concrete-product: information-dialog}
 *
 * @param dialogConfig [DialogConfig] for the underlying-system
 * @param message the information to be displayed
 */
class SimpleDialog(
    dialogConfig: DialogConfig,
    private val message: String
) : SystemDialog(dialogConfig) {

    /**
     * Shows the [message] on screen
     */
    override fun showContents() {
        println("> Message: $message")
    }
}

/**
 * [SystemDialog] that shows an information-message along with an action on the screen
 * {concrete-product: action-dialog}
 *
 * @param dialogConfig [DialogConfig] for the underlying-system
 * @param message the information to be displayed
 * @param action the action that can be performed on this dialog
 *
 */
class ActionDialog(
    dialogConfig: DialogConfig,
    private val message: String,
    private val action: String
) : SystemDialog(dialogConfig) {

    /**
     * Show [message] and [action] on the screen
     */
    override fun showContents() {
        println("> $message")
        println("[$action]")
    }
}

/**
 * Abstraction-layer for implementing a Factory for creating [SystemDialog]s for
 * different underlying-systems.
 *
 * {abstract-factory: system-dialog-factory}
 *
 */
interface SystemDialogFactory {

    /**
     * Creates instance of [SimpleDialog] for a given system-implementation
     *
     * {factory-method: information-dialog}
     */
    fun createDialog(message: String): SimpleDialog

    /**
     * Creates instance of [ActionDialog] for a given system-implementation
     *
     * {factory-method: action-dialog}
     */
    fun createActionDialog(message: String, action: String): ActionDialog
}

/**
 * [SystemDialogFactory] implementation for creating [SystemDialog]s for Windows
 *
 * {concrete-factory: windows-dialog-factory}
 */
class WindowsDialogFactory : SystemDialogFactory {

    /**
     * Creates instance of [SimpleDialog] with [DialogConfig.Windows] configuration
     */
    override fun createDialog(message: String) =
        SimpleDialog(DialogConfig.Windows, message)

    /**
     * Creates instance of [ActionDialog] with [DialogConfig.Windows] configuration
     */
    override fun createActionDialog(message: String, action: String) =
        ActionDialog(DialogConfig.Windows, message, action)
}

/**
 * [SystemDialogFactory] implementation for creating [SystemDialog]s for MacOs
 *
 * {concrete-factory: mac-os-dialog-factory}
 */
class MacOsDialogFactory : SystemDialogFactory {

    /**
     * Creates instance of [SimpleDialog] with [DialogConfig.MacOs] configuration
     */
    override fun createDialog(message: String) =
        SimpleDialog(DialogConfig.MacOs, message)

    /**
     * Creates instance of [SimpleDialog] with [DialogConfig.MacOs] configuration
     */
    override fun createActionDialog(message: String, action: String) =
        ActionDialog(DialogConfig.MacOs, message, action)
}

/* playground */
fun main() {

    listOf<(SystemDialogFactory) -> SystemDialog>(
        { it.createActionDialog("This is a sample message", "Ok") },
        { it.createDialog("This is a sample message") }
    ).random()(arrayOf(MacOsDialogFactory(), WindowsDialogFactory()).random()).show()
}
