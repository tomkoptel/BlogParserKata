package com.tom.kata.coroutines.parser.testkit

import android.support.test.InstrumentationRegistry
import com.tom.kata.coroutines.parser.ParserApp

/**
 * Exposes app DI component to later override its dependencies.
 */
object AppUnderTest {
    fun component(): ParserApp.Component {
        return ParserApp.component(InstrumentationRegistry.getTargetContext())
    }
}