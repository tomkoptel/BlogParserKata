package com.tom.kata.coroutines.parser.utils

import android.content.res.Resources
import android.view.View
import com.tom.kata.coroutines.parser.R
import java.util.*

/**
 * Returns random primary color.
 */
fun View.generateRandomPrimaryColor(): Int {
    val array = resources.getIntArray(R.array.primaryColors)
    val randomIndex = Random().nextInt(array.size - 1)
    return array[randomIndex]
}

/**
 * Converts px -> dp
 */
val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

/**
 * Converts dp -> px
 */
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()