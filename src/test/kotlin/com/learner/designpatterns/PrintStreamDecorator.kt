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

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream
import java.io.PrintStream
import java.util.*

/**
 * Decorator for PrintStreams
 * Enables printing to both Decorator and Decorated Streams
 *
 * Useful when it is required to get data from Console and reset it,
 * simultaneously with retaining the output at Console
 *
 * Created by Rishabh on 01-06-2020
 */
class PrintStreamDecorator(
    private val printStream: PrintStream,
    private val console: ByteOutputStream = ByteOutputStream()
) : PrintStream(console) {

    /**
     * Retrieves the data printed on Console since last call to this
     * function, and resets it.
     */
    fun getConsoleData(): String = console.toString().also {
        console.reset()
    }

    /**
     * Provides the decorated print-streams
     *
     * @returns the instance of print-stream inside this decorator
     */
    fun getDecoratedPrintStream(): PrintStream = printStream

    override fun println() {
        printStream.println()
        super.println()
    }

    override fun println(x: Boolean) {
        printStream.println(x)
        super.println(x)
    }

    override fun println(x: Char) {
        printStream.println(x)
        super.println(x)
    }

    override fun println(x: Int) {
        printStream.println(x)
        super.println(x)
    }

    override fun println(x: Long) {
        printStream.println(x)
        super.println(x)
    }

    override fun println(x: Float) {
        printStream.println(x)
        super.println(x)
    }

    override fun println(x: Double) {
        printStream.println(x)
        super.println(x)
    }

    override fun println(x: CharArray) {
        printStream.println(x)
        super.println(x)
    }

    override fun println(x: String?) {
        printStream.println(x)
        super.println(x)
    }

    override fun println(x: Any?) {
        printStream.println(x)
        super.println(x)
    }

    override fun print(b: Boolean) {
        printStream.print(b)
        super.print(b)
    }

    override fun print(c: Char) {
        printStream.print(c)
        super.print(c)
    }

    override fun print(i: Int) {
        printStream.print(i)
        super.print(i)
    }

    override fun print(l: Long) {
        printStream.print(l)
        super.print(l)
    }

    override fun print(f: Float) {
        printStream.print(f)
        super.print(f)
    }

    override fun print(d: Double) {
        printStream.print(d)
        super.print(d)
    }

    override fun print(s: CharArray) {
        printStream.print(s)
        super.print(s)
    }

    override fun print(s: String?) {
        printStream.print(s)
        super.print(s)
    }

    override fun print(obj: Any?) {
        printStream.print(obj)
        super.print(obj)
    }

    override fun write(b: Int) {
        printStream.write(b)
        super.write(b)
    }

    override fun write(buf: ByteArray, off: Int, len: Int) {
        printStream.write(buf, off, len)
        super.write(buf, off, len)
    }

    override fun flush() {
        printStream.flush()
        super.flush()
    }

    override fun checkError(): Boolean {
        printStream.checkError()
        return super.checkError()
    }

    override fun append(csq: CharSequence?): PrintStream {
        printStream.append(csq)
        return super.append(csq)
    }

    override fun append(csq: CharSequence?, start: Int, end: Int): PrintStream {
        printStream.append(csq, start, end)
        return super.append(csq, start, end)
    }

    override fun append(c: Char): PrintStream {
        printStream.append(c)
        return super.append(c)
    }

    override fun format(format: String, vararg args: Any?): PrintStream {
        printStream.format(format, *args)
        return super.format(format, *args)
    }

    override fun format(l: Locale?, format: String, vararg args: Any?): PrintStream {
        printStream.format(l, format, *args)
        return super.format(l, format, *args)
    }

    override fun printf(format: String, vararg args: Any?): PrintStream {
        printStream.printf(format, *args)
        return super.printf(format, *args)
    }

    override fun printf(l: Locale?, format: String, vararg args: Any?): PrintStream {
        printStream.printf(l, format, *args)
        return super.printf(l, format, *args)
    }

    override fun close() {
        printStream.close()
        super.close()
    }
}