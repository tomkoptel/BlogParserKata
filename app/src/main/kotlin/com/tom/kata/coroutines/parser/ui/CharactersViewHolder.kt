package com.tom.kata.coroutines.parser.ui

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.tom.kata.coroutines.parser.R
import com.tom.kata.coroutines.parser.domain.ParseResult

/**
 * Boilerplate view holder to represent [ParseResult.Characters] data type.
 */
class CharactersViewHolder(view: View) : ResultViewHolder<UiEntity>(view) {
    val charactersRecyclerView = view.findViewById<RecyclerView>(R.id.charactersRecyclerView)!!.apply {
        layoutManager = WrapContentLinearLayoutManager(
            view.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        isDrawingCacheEnabled = true
        drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        hasFixedSize()
        setItemViewCacheSize(20)
    }

    override fun bind(entity: UiEntity) {
        val result = (entity as UiEntity.Item).result as ParseResult.Characters
        charactersRecyclerView.adapter = CharactersAdapter(result.values).apply {
            hasStableIds()
        }
    }

    companion object : ViewHolderCreator<CharactersViewHolder>(R.layout.characters_layout, {
        CharactersViewHolder(it)
    })
}