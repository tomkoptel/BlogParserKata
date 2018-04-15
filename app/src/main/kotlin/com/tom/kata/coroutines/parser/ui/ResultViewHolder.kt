package com.tom.kata.coroutines.parser.ui

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Base abstract view holder.
 */
abstract class ResultViewHolder<in Entity>(view: View): RecyclerView.ViewHolder(view) {
    abstract fun bind(entity: Entity)
}