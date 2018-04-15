package com.tom.kata.coroutines.parser.testkit

import android.support.test.espresso.IdlingResource
import kotlinx.coroutines.experimental.Job

/**
 * Based on the issue from [Github coroutines repo](https://github.com/Kotlin/kotlinx.coroutines/issues/242).
 */
fun Job.asIdlingResource() = object : IdlingResource {
    override fun getName() = "Coroutine job ${this@asIdlingResource}"

    override fun isIdleNow(): Boolean {
        return isCompleted
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        invokeOnCompletion {
            callback.onTransitionToIdle()
        }
    }
}