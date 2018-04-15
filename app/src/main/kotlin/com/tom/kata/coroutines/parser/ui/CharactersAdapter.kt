package com.tom.kata.coroutines.parser.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tom.kata.coroutines.parser.R
import com.tom.kata.coroutines.parser.domain.ParseResult
import com.tom.kata.coroutines.parser.utils.generateRandomPrimaryColor
import com.tom.kata.coroutines.parser.utils.px

/**
 * Boilerplate code to create horizontal recycler view on the page.
 */
class CharactersAdapter(private val characters: List<ParseResult.Character>): RecyclerView.Adapter<ResultViewHolder<ParseResult.Character>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        return CharacterHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    override fun onBindViewHolder(holder: ResultViewHolder<ParseResult.Character>, position: Int) {
        holder.bind(characters[position])
    }

    class CharacterHolder(view: View) : ResultViewHolder<ParseResult.Character>(view) {
        private val characterValue = view.findViewById<TextView>(R.id.characterValue)!!
        private val characterAscii = view.findViewById<TextView>(R.id.characterAscii)!!

        override fun bind(entity: ParseResult.Character) {
            entity.let {
                characterValue.text = it.char.toString()
                characterAscii.text = it.unicode
            }
        }

        companion object : ViewHolderCreator<CharacterHolder>(R.layout.character_layout, {
            val viewGroup = it as ViewGroup
            val resources = it.resources

            viewGroup.apply {
                layoutParams = layoutParams.apply {
                    height = resources.getDimension(R.dimen.small_card_height).toInt().px
                    width = resources.getDimension(R.dimen.small_card_width).toInt().px
                }
                setBackgroundColor(it.generateRandomPrimaryColor())
            }
            CharacterHolder(viewGroup)
        })
    }
}