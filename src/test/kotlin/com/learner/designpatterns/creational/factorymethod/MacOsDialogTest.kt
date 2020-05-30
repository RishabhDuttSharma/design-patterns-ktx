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

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

/**
 * Test Cases for [MacOsDialog]
 *
 * Created by Rishabh on 30-05-2020
 */
internal class MacOsDialogTest : BasePrintStreamTest() {

    /**
     * Verifies that whether the correct createButton() is invoked internally
     */
    @Test
    fun createButton() = MacOsDialog().run {
        // perform render action
        render()
        // gather console output
        val consoleOutput = getConsoleOutput().trim()
        // render() -> button.draw() -> console output
        assertFalse(consoleOutput.isEmpty())
        // console output should match the target contents
        assertEquals("MacOS -> Drawing Button using XCode Tools...", consoleOutput)
    }
}