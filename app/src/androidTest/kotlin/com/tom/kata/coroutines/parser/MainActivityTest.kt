package com.tom.kata.coroutines.parser

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.runner.AndroidJUnit4
import android.view.View
import com.tom.kata.coroutines.parser.testkit.childAtPosition
import com.tom.kata.coroutines.parser.testkit.setupEnvAndLaunchActivity
import okreplay.OkReplay
import okreplay.TapeMode
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Provides hermetic UI test for the application.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @JvmField @Rule val rule = setupEnvAndLaunchActivity<MainActivity>()

    @Test
    @OkReplay(mode = TapeMode.READ_ONLY)
    fun first_character_should_be_space() {
        val firstCharacter = onView(
            allOf<View>(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recyclerView),
                        1
                    ), 1
                ),
                isDisplayed()
            )
        )
        firstCharacter.check(matches(withText("20")))
    }
}