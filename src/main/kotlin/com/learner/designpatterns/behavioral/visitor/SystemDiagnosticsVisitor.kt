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
 * Created by Rishabh on 07-06-2020
 */
interface SystemDiagnosticsVisitor {

    /**
     * Visits IO-System for Diagnostics
     */
    fun visit(system: InputOutputSystem)

    /**
     * Visits Network-System for Diagnostics
     */
    fun visit(system: NetworkSystem)

    /**
     * Visits Processing-System for Diagnostics
     */
    fun visit(system: ProcessingSystem)

    /**
     * Visits Storage-System for Diagnostics
     */
    fun visit(system: StorageSystem)
}

interface System {

    fun accept(visitor: SystemDiagnosticsVisitor)
}

class StorageSystem : System {

    override fun accept(visitor: SystemDiagnosticsVisitor) {
        visitor.visit(this)
    }
}

class NetworkSystem : System {

    override fun accept(visitor: SystemDiagnosticsVisitor) {
        visitor.visit(this)
    }
}

class ProcessingSystem : System {

    override fun accept(visitor: SystemDiagnosticsVisitor) {
        visitor.visit(this)
    }
}

class InputOutputSystem : System {

    override fun accept(visitor: SystemDiagnosticsVisitor) {
        visitor.visit(this)
    }
}