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

package com.learner.designpatterns.behavioral.chainofresponsibility

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Test Cases for [EncoderDecoder]
 *
 * Created by Rishabh on 01-06-2020
 */
internal class EncoderDecoderTest {

    /**
     * Verifies that the data encoded and decoded by [EncoderDecoder] is correct.
     */
    @Test
    fun encodeDecode_validInput_returnsSuccess() {
        val testInput = "sample"
        // perform encoding
        val encodedTestInput = EncoderDecoder.encode(testInput)
        // perform decoding of encoded-data
        val decodedTestInput = EncoderDecoder.decode(encodedTestInput)
        // decoded input should match the original input before encoding
        Assertions.assertThat(decodedTestInput).isEqualTo(testInput)
    }
}