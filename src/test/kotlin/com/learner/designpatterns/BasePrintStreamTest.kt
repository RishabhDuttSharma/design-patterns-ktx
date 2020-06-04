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

package com.learner.designpatterns

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll

/**
 * Base Class to facilitate testing the values printed to the Console
 *
 * Supports: System.err and System.out
 *
 * Created by Rishabh on 30-05-2020
 */
open class BasePrintStreamTest {

    private val outStream = PrintStreamDecorator(System.out)
    private val errStream = PrintStreamDecorator(System.err)

    /**
     * Sets up custom output stream, and error stream
     */
    @BeforeAll
    fun setUpStreams() {
        System.setOut(outStream)
        System.setErr(errStream)
    }

    /**
     * Restores output stream and error stream to their default states
     */
    @AfterAll
    fun restoreStreams() {
        System.setOut(outStream.getDecoratedPrintStream())
        System.setErr(errStream.getDecoratedPrintStream())
    }

    /**
     * @return the Output data printed in console since last call to this method
     */
    protected fun getConsoleOutput() = outStream.getConsoleData()

    /**
     * @return the Error data printed in console since last call to this method
     */
    protected fun getConsoleError() = errStream.getConsoleData()
}