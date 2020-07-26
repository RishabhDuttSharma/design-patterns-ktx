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
 *
 * Created by Rishabh on 26-07-2020
 */

enum class SystemConfig(val systemType: String) {
    Windows("Windows-Dialog"),
    MacOs("MacOs-Dialog")
}

abstract class SystemDialog(private val systemConfig: SystemConfig) {

    abstract fun showContents()

    fun show() {
        println("======= ${systemConfig.systemType} =======")
        showContents()
        println("======= END =======")
    }
}

class SimpleDialog(
    systemConfig: SystemConfig,
    private val message: String
) : SystemDialog(systemConfig) {

    override fun showContents() {
        println("Message: $message")
    }
}

class ActionDialog(
    systemConfig: SystemConfig,
    private val message: String,
    private val action: String
) : SystemDialog(systemConfig) {

    override fun showContents() {
        println("Message: $message")
        println("Action: $action")
    }
}

interface SystemDialogFactory {

    fun createDialog(message: String): SimpleDialog

    fun createActionDialog(message: String, action: String): ActionDialog
}

class WindowsDialogFactory : SystemDialogFactory {

    override fun createDialog(message: String): SimpleDialog {
        return SimpleDialog(SystemConfig.Windows, message)
    }

    override fun createActionDialog(message: String, action: String): ActionDialog {
        return ActionDialog(SystemConfig.Windows, message, action)
    }
}

class MacOsDialogFactory : SystemDialogFactory {

    override fun createDialog(message: String): SimpleDialog {
        return SimpleDialog(SystemConfig.MacOs, message)
    }

    override fun createActionDialog(message: String, action: String): ActionDialog {
        return ActionDialog(SystemConfig.MacOs, message, action)
    }
}

fun main() {

    arrayOf(MacOsDialogFactory(), WindowsDialogFactory())
        .random()
        .createActionDialog("Hello Dialog World", "Ok")
        .show()
}
