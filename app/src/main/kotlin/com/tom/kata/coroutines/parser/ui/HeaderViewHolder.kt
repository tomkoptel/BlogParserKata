package com.tom.kata.coroutines.parser.ui

import android.view.View
import android.widget.TextView
import com.tom.kata.coroutines.parser.R

/**
 * Boilerplate view holder to represent [UiEntity.Header] data type.
 */
class HeaderViewHolder(view: View) : ResultViewHolder<UiEntity>(view) {
    private val headerLabel = view.findViewById<TextView>(R.id.headerLabel)!!

    override fun bind(entity: UiEntity) {
        val result = (entity as UiEntity.Header)
        headerLabel.text = result.label
    }

    companion object : ViewHolderCreator<HeaderViewHolder>(R.layout.header_layout, {
        HeaderViewHolder(it)
    })
}