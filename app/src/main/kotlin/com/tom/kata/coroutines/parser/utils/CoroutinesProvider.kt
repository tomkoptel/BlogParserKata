package com.tom.kata.coroutines.parser.utils

import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.CoroutineStart
import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.Job
import kotlin.coroutines.experimental.CoroutineContext
import kotlinx.coroutines.experimental.launch as coroutineLaunch

/**
 * Acts as dependency provider. Later used in the test env to provide suspendable UI tests.
 */
interface CoroutinesProvider {
    fun launch(
        context: CoroutineContext = DefaultDispatcher,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        parent: Job? = null,
        block: suspend CoroutineScope.() -> Unit
    ): Job

    companion object {
        fun default(): CoroutinesProvider = object :
            CoroutinesProvider {
            override fun launch(
                context: CoroutineContext,
                start: CoroutineStart,
                parent: Job?,
                block: suspend CoroutineScope.() -> Unit
            ): Job {
                return coroutineLaunch(context, start, parent, block)
            }
        }
    }
}
