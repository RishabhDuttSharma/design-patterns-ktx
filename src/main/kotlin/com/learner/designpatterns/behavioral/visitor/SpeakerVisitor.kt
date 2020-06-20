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

/**
 * Represents a Speaker.
 *
 * It accepts a [SpeakerVisitor] to allow addition of new-behaviour
 * at run-time. Behaviour may differ across various implementations
 * of [SpeakerVisitor].
 *
 * Created by Rishabh on 15-06-2020
 */
class Speaker(private val name: String) {

    // holds the current-state of Speaker
    internal var state: String = "stopped"
        set(value) {
            field = value
            println("$name is $value")
        }

    /**
     * Accepts a [SpeakerVisitor] (e.g., from outside), and asks it to visit
     * this instance of [Speaker] to perform intended operation
     */
    fun accept(visitor: SpeakerVisitor) {
        visitor.visit(this)
    }
}

/**
 * Defines the contract for implementing a Visitor for [Speaker]
 *
 * A [Speaker] accepts an instance of [SpeakerVisitor] to allow
 * execution of a new behaviour at runtime
 */
interface SpeakerVisitor {

    /**
     * Visits a [Speaker], and performs required operation on it
     */
    fun visit(speaker: Speaker)
}

/**
 * Defines PLAY runtime-behaviour on the [Speaker].
 *
 * Concrete implementation of [SpeakerVisitor] that visits a [Speaker],
 * and starts playing it.
 */
class PlayVisitor : SpeakerVisitor {

    /**
     * Visits [speaker], and resumes it by setting its state to "playing"
     */
    override fun visit(speaker: Speaker) {
        speaker.state = "playing"
    }
}

/**
 * Defines PAUSE runtime-behaviour on the [Speaker].
 *
 * Concrete implementation of [SpeakerVisitor] that visits a [Speaker],
 * and pauses it.
 */
class PauseVisitor : SpeakerVisitor {

    /**
     * Visits [speaker], and temporarily interrupts it by setting its
     * state to "paused"
     */
    override fun visit(speaker: Speaker) {
        speaker.state = "paused"
    }
}

/**
 * Defines STOP runtime-behaviour on the [Speaker]
 *
 * Concrete implementation of [SpeakerVisitor] that visits a [Speaker]
 * and stops it.
 */
class StopVisitor : SpeakerVisitor {

    /**
     * Visits [speaker], and interrupts it by setting its state to "stopped"
     */
    override fun visit(speaker: Speaker) {
        speaker.state = "stopped"
    }
}

/** playground */
fun main() {
    // creates a speaker
    val speaker = Speaker("Bose")
    // creates a list of visitors
    val visitors = arrayOf(PlayVisitor(), PauseVisitor(), StopVisitor())
    // asks the speaker to accepts visitors to change its state
    visitors.forEach(speaker::accept)
}