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
 *
 * Created by Rishabh on 15-06-2020
 */
class Speaker(private val name: String) {

    internal var state: String = "stopped"
        set(value) {
            field = value
            println("$name is $value")
        }

    fun accept(visitor: SpeakerVisitor) {
        visitor.visit(this)
    }
}

interface SpeakerVisitor {
    fun visit(speaker: Speaker)
}

class PlayVisitor : SpeakerVisitor {
    override fun visit(speaker: Speaker) {
        speaker.state = "playing"
    }
}

class PauseVisitor : SpeakerVisitor {
    override fun visit(speaker: Speaker) {
        speaker.state = "paused"
    }
}

class StopVisitor : SpeakerVisitor {
    override fun visit(speaker: Speaker) {
        speaker.state = "stopped"
    }
}

fun main() {

    val speaker = Speaker("Bose")
    val visitors = arrayOf(PlayVisitor(), PauseVisitor(), StopVisitor())
    for (i in 0 until 10) {
        speaker.accept(visitors.random())
    }
}