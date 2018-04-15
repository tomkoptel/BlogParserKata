package com.tom.kata.coroutines.parser.ui

import com.tom.kata.coroutines.parser.domain.ParseResult

/**
 * Data types that represent variety of components being drawn on UI.
 */
sealed class UiEntity(open val type: Type): Comparable<UiEntity> {
    override fun compareTo(other: UiEntity): Int {
        return type.compareTo(other.type)
    }

    class Item(val result: ParseResult, override val type: Type) : UiEntity(type)

    class Header(val label: String) : UiEntity(Type.HEADER)

    enum class Type {
        CHARACTER, CHARACTERS, WORDS, HEADER;
    }
}