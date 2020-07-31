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

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Test-Cases for [WindowsDialogFactory]
 *
 * Created by Rishabh on 31-07-2020
 */
internal class WindowsDialogFactoryTest {

    @Test
    fun `verify createDialog() returns SimpleDialog`() {
        val dialog = WindowsDialogFactory().createDialog("test-message")
        Assertions.assertThat(dialog).isExactlyInstanceOf(SimpleDialog::class.java)
    }

    @Test
    fun `verify createActionDialog() returns ActionDialog`() {
        val dialog = WindowsDialogFactory().createActionDialog("test-message", "test-action")
        Assertions.assertThat(dialog).isExactlyInstanceOf(ActionDialog::class.java)
    }
}