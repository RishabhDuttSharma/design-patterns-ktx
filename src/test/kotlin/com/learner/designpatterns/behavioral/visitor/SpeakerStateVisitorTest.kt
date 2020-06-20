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

package com.learner.designpatterns.behavioral.visitor

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Test-Cases for [SpeakerStateVisitor] implementations
 * i.e.,
 *
 * Created by Rishabh on 20-06-2020
 */
internal class SpeakerStateVisitorTest {

    private lateinit var speaker: Speaker

    @BeforeEach
    fun setupSpeaker() {
        // arrange
        speaker = Speaker("Bose")
    }

    /**
     * Verifies that [SpeakerPlayVisitor] sets correct state
     * on the [Speaker] i.e., playing
     */
    @Test
    fun `test visit SpeakerPlayVisitor`() {
        // act
        SpeakerPlayVisitor().visit(speaker)
        // assert
        Assertions.assertThat(speaker.state).isEqualTo("playing")
    }

    /**
     * Verifies that [SpeakerPauseVisitor] sets correct state
     * on the [Speaker] i.e., paused
     */
    @Test
    fun `test visit SpeakerPauseVisitor`() {
        // act
        SpeakerPauseVisitor().visit(speaker)
        // assert
        Assertions.assertThat(speaker.state).isEqualTo("paused")
    }

    /**
     * Verifies that [SpeakerStopVisitor] sets correct state
     * on the [Speaker] i.e., stopped
     */
    @Test
    fun `test visit SpeakerStopVisitor`() {
        // act
        SpeakerStopVisitor().visit(speaker)
        // assert
        Assertions.assertThat(speaker.state).isEqualTo("stopped")
    }
}