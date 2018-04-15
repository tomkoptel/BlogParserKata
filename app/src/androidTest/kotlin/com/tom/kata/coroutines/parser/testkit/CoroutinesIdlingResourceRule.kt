package com.tom.kata.coroutines.parser.testkit

import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.IdlingResource
import com.tom.kata.coroutines.parser.utils.CoroutinesProvider
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.CoroutineStart
import kotlinx.coroutines.experimental.Job
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import kotlin.coroutines.experimental.CoroutineContext
import kotlinx.coroutines.experimental.launch as coroutineLaunch

/**
 * Following [TestRule] ensures that Espresso waits for the underlying coroutines to finish their execution. We
 * perform the fit by overriding Singleton component that serves as simplified DI implementation in the project.
 */
class CoroutinesIdlingResourceRule : TestRule {
    private val resources = mutableListOf<IdlingResource>()
    private val idlingRegistry = IdlingRegistry.getInstance()

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                overrideDefaultCoroutinesProvider()

                base.evaluate()

                resources.forEach { idlingRegistry.unregister(it) }
            }
        }
    }

    private fun overrideDefaultCoroutinesProvider() {
        AppUnderTest.component().coroutinesProvider = object : CoroutinesProvider {
            override fun launch(
                context: CoroutineContext,
                start: CoroutineStart,
                parent: Job?,
                block: suspend CoroutineScope.() -> Unit
            ): Job {
                // Use original couroutoine factory function
                val job = coroutineLaunch(context, start, parent, block)
                // Tranform job to IdlingResource and register in the Espresso
                job.asIdlingResource().let {
                    idlingRegistry.register(it)
                    resources.add(it)
                }
                return job
            }
        }
    }
}