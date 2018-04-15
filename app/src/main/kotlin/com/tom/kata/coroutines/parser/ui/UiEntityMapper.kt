package com.tom.kata.coroutines.parser.ui

import android.content.Context
import com.tom.kata.coroutines.parser.R
import com.tom.kata.coroutines.parser.domain.ParseResult

/**
 * Mapper that stands in the middle between domain data types and UI data types. In additional mixes in headers to
 * the UI for the better separation of sections.
 */
class UiEntityMapper(context: Context) {
    private val resources = context.resources

    fun mapTo(results: List<ParseResult>): List<UiEntity> {
        val uiEntities = mutableListOf<UiEntity>()
        results.mapTo(uiEntities, {
            val type = when(it) {
                is ParseResult.Character -> UiEntity.Type.CHARACTER
                is ParseResult.Characters -> UiEntity.Type.CHARACTERS
                is ParseResult.Word -> UiEntity.Type.WORDS
            }
            UiEntity.Item(it, type)
        })

        val characterElIndex = uiEntities.indexOfFirst { it.type == UiEntity.Type.CHARACTER }
        if (characterElIndex > -1){
            uiEntities.add(characterElIndex,
                UiEntity.Header(resources.getString(R.string.first_10_th_character))
            )
        }

        val charactersElIndex = uiEntities.indexOfFirst { it.type == UiEntity.Type.CHARACTERS }
        if (charactersElIndex > -1) {
            uiEntities.add(charactersElIndex,
                UiEntity.Header(resources.getString(R.string.every_10_th_character))
            )
        }

        val wordsElIndex = uiEntities.indexOfFirst { it.type == UiEntity.Type.WORDS }
        if (wordsElIndex > -1) {
            uiEntities.add(wordsElIndex,
                UiEntity.Header(resources.getString(R.string.word_frequencies))
            )
        }

        return uiEntities
    }
}