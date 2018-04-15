package com.tom.kata.coroutines.parser.domain

/**
 * Represents data structure of 3 primary parse results.
 */
sealed class ParseResult {
    /**
     * Represents parse result of single character.
     */
    data class Character(
        val char: Char,
        val unicode: String = Integer.toHexString(char.toInt())
    ) : ParseResult() {
        companion object Mapper {
            fun mapToList(char: Char): List<Character> {
                return listOf(Character(char = char))
            }
        }
    }

    /**
     * Represents result of parsed characters sequence.
     */
    data class Characters(
        val values: List<Character>
    ) : ParseResult() {
        companion object Mapper {
            fun mapToList(chars: List<Char>): List<Characters> {
                val values = mutableListOf<Character>()
                chars.mapTo(values) { Character(char = it) }
                return listOf(Characters(values))
            }
        }
    }

    /**
     * Represents result of parse words. It is no more then pair based structure.
     */
    data class Word(
        val word: String,
        val frequency: Int
    ) : ParseResult() {
        companion object Mapper {
            fun mapToList(map: Map<String, Int>): List<Word> {
                val list = mutableListOf<Word>()
                map.mapTo(list) { Word(it.key, it.value) }.sortByDescending { it.frequency }
                return list
            }
        }
    }
}