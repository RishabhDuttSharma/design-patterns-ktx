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

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import java.io.ByteArrayOutputStream
import java.io.PrintStream

/**
 * Base Class to facilitate testing the values printed to the Console
 *
 * Supports: System.err and System.out
 *
 * Created by Rishabh on 30-05-2020
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class BasePrintStreamTest {

    private val consoleOut = ByteArrayOutputStream()
    private val consoleErr = ByteArrayOutputStream()

    private val actualSystemOut = System.out
    private val actualSystemErr = System.err

    /**
     * Sets up custom output stream, and error stream
     */
    @BeforeAll
    fun setUpStreams() {
        System.setOut(PrintStream(consoleOut))
        System.setErr(PrintStream(consoleErr))
    }

    /**
     * Restores output stream and error stream to their default states
     */
    @AfterAll
    fun restoreStreams() {
        System.setOut(actualSystemOut)
        System.setErr(actualSystemErr)
    }

    /**
     * @return the Output data printed in console since last call to this method
     */
    protected fun getConsoleOutput() = getConsoleAndReset(consoleOut)

    /**
     * @return the Error data printed in console since last call to this method
     */
    protected fun getConsoleError() = getConsoleAndReset(consoleErr)

    /**
     * Gets data from console and resets it
     *
     * @return current data in the Console
     */
    private fun getConsoleAndReset(console: ByteArrayOutputStream): String {
        val consoleData = console.toString()
        console.reset()
        return consoleData
    }
}