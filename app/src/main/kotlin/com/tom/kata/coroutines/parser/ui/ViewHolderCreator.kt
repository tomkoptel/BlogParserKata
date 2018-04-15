package com.tom.kata.coroutines.parser.ui

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Base class for creating view holders. Reduces boilerplate for creating custom view holders.
 */
abstract class ViewHolderCreator<out Holder>(@LayoutRes private val layout: Int, private val factory: (View) -> Holder) {
    fun create(parent: ViewGroup): Holder = factory.invoke(onCreateViewHolder(parent))

    private fun onCreateViewHolder(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(layout, parent, false)
    }
}