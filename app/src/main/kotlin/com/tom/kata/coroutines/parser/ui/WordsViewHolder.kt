package com.tom.kata.coroutines.parser.ui

import android.view.View
import android.widget.TextView
import com.tom.kata.coroutines.parser.R
import com.tom.kata.coroutines.parser.domain.ParseResult
import com.tom.kata.coroutines.parser.utils.generateRandomPrimaryColor

/**
 * Boilerplate view holder to represent [ParseResult.Word] data type.
 */
class WordsViewHolder(view: View) : ResultViewHolder<UiEntity>(view) {
    private val characterValue = view.findViewById<TextView>(R.id.characterValue)!!
    private val frequencyNumber = view.findViewById<TextView>(R.id.frequencyNumber)!!

    override fun bind(entity: UiEntity) {
        val result = (entity as UiEntity.Item).result as ParseResult.Word
        result.let {
            characterValue.text = it.word
            frequencyNumber.text = it.frequency.toString()
        }
    }

    companion object : ViewHolderCreator<WordsViewHolder>(R.layout.word_layout, {
        WordsViewHolder(it.apply { setBackgroundColor(it.generateRandomPrimaryColor()) })
    })
}
