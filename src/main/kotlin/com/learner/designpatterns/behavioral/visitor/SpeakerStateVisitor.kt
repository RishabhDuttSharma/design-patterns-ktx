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
 * SpeakerStateVisitor
 * --------------------
 * A Speaker system can be switched between various states e.g., playing,
 * paused, stopped, etc. Apart from these states, it might be required to
 * have additional behaviours. Implementing these behaviours directly into
 * the class can violate both Open-Close and Single-Responsibility principle.
 *
 * To counter this problem, we can define a contract in such a way that
 * whenever a new functionality is required, it can be added to the Speaker
 * at runtime. In return, the Speaker then invokes it, and the operation
 * represented by the functionality will be performed.
 *
 * For this, the Speaker accepts a functionality, asks it to visit it and
 * perform the intended operation. Since, the new-behaviour visits the host,
 * it is called a Visitor. And the pattern is called Visitor-Pattern.
 *
 * In visitor-pattern, at-first the visitor is accepted by the host, and secondly,
 * the visitor is invoked by the host to visit it. Since the runtime-behaviour
 * is achieved by two calls, this technique is also called Double-Dispatch.
 *
 * Created by Rishabh on 15-06-2020
 */

// implementation

/**
 * Represents a Speaker.
 *
 * It accepts a [SpeakerStateVisitor] to allow addition of new-behaviour
 * at run-time. Behaviour may differ across various implementations
 * of [SpeakerStateVisitor].
 */
class Speaker(private val name: String) {

    // holds the current-state of Speaker
    internal var state: String = "stopped"
        set(value) {
            field = value
            println("$name is $value")
        }

    /**
     * Accepts a [SpeakerStateVisitor] (e.g., from outside), and asks it to visit
     * this instance of [Speaker] to perform intended operation
     */
    fun accept(visitor: SpeakerStateVisitor) {
        visitor.visit(this)
    }
}

/**
 * Defines the contract for implementing a Visitor for [Speaker]
 *
 * A [Speaker] accepts an instance of [SpeakerStateVisitor] to allow
 * execution of a new behaviour at runtime
 */
interface SpeakerStateVisitor {

    /**
     * Visits a [Speaker], and performs required operation on it
     */
    fun visit(speaker: Speaker)
}

/**
 * Defines PLAY runtime-behaviour on the [Speaker].
 *
 * Concrete implementation of [SpeakerStateVisitor] that visits a [Speaker],
 * and starts playing it.
 */
class SpeakerPlayVisitor : SpeakerStateVisitor {

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
 * Concrete implementation of [SpeakerStateVisitor] that visits a [Speaker],
 * and pauses it.
 */
class SpeakerPauseVisitor : SpeakerStateVisitor {

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
 * Concrete implementation of [SpeakerStateVisitor] that visits a [Speaker]
 * and stops it.
 */
class SpeakerStopVisitor : SpeakerStateVisitor {

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
    val visitors = arrayOf(SpeakerPlayVisitor(), SpeakerPauseVisitor(), SpeakerStopVisitor())
    // asks the speaker to accepts visitors to change its state
    visitors.forEach(speaker::accept)
}