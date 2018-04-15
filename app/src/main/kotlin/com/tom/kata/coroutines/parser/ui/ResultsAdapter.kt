package com.tom.kata.coroutines.parser.ui

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.tom.kata.coroutines.parser.ui.UiEntity.Type.*


/**
 * Primary adapter that orchestrates the look on the screen. For the sake of simplicity was implemented in
 * boilerplate manner.
 */
class ResultsAdapter : RecyclerView.Adapter<ResultViewHolder<UiEntity>>() {
    private var data = emptyList<UiEntity>()
    // We use dedicated view pool to optimize UI rendering
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder<UiEntity> {
        val resultType = UiEntity.Type.values()[viewType]

        return when (resultType) {
            CHARACTER -> CharacterHolder.create(parent)
            WORDS -> WordsViewHolder.create(parent)
            HEADER -> HeaderViewHolder.create(parent)
            CHARACTERS -> {
                CharactersViewHolder.create(parent).apply {
                    charactersRecyclerView.recycledViewPool = viewPool
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ResultViewHolder<UiEntity>, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemViewType(position: Int): Int {
        // The view types represented as the enums. It is not the prettiest way to handle multiple view types.
        return data[position].type.ordinal
    }

    override fun getItemCount(): Int = data.size

    fun addAll(values: List<UiEntity>) {
        data = values
        notifyDataSetChanged()
    }
}