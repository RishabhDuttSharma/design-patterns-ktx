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

import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Test

/**
 * Test-Cases for [SystemDialog]
 *
 * Created by Rishabh on 26-07-2020
 */
internal class SystemDialogTest {

    @Test
    fun `verify showContents() method is invoked by show()`() {

        // create an instance of SystemDialog that exposes showContents
        val spySystemDialog = createSpySystemDialog()

        // invoke show() method
        spySystemDialog.show()

        // verify whether showContents() method is invoked exactly once
        verify(exactly = 1) { spySystemDialog.showContents() }
    }

    @Test
    fun `verify that show() is always called exactly once when invoked`() {

        // create an instance of SystemDialog that exposes showContents
        val spySystemDialog = createSpySystemDialog()

        // invoke show() method
        spySystemDialog.show()

        // verify that none calls are made to show() other than the caller
        verify(exactly = 1) { spySystemDialog.show() }
    }

    /**
     * Creates and returns a Spy for SystemDialog that exposes [SystemDialog.showContents]
     * for testing
     */
    private fun createSpySystemDialog() = spyk(object : SystemDialog(DialogConfig.Windows) {
        public override fun showContents() {
            // expose visibility by using public modifier
        }
    })
}