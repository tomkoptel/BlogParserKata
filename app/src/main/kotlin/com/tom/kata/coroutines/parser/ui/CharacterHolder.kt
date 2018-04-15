package com.tom.kata.coroutines.parser.ui

import android.view.View
import android.widget.TextView
import com.tom.kata.coroutines.parser.R
import com.tom.kata.coroutines.parser.domain.ParseResult

/**
 * Boilerplate view holder to represent [ParseResult.Character] data type.
 */
class CharacterHolder(view: View) : ResultViewHolder<UiEntity>(view) {
    private val characterValue = view.findViewById<TextView>(R.id.characterValue)!!
    private val characterAscii = view.findViewById<TextView>(R.id.characterAscii)!!

    override fun bind(entity: UiEntity) {
        val result = (entity as UiEntity.Item).result as ParseResult.Character
        result.let {
            characterValue.text = it.char.toString()
            characterAscii.text = it.unicode
        }
    }

    companion object : ViewHolderCreator<CharacterHolder>(R.layout.character_layout, {
        CharacterHolder(it)
    })
}
