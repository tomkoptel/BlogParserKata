package com.tom.kata.coroutines.parser

import com.tom.kata.coroutines.parser.domain.allNthCharacters
import com.tom.kata.coroutines.parser.domain.firstNthCharacter
import com.tom.kata.coroutines.parser.domain.wordFrequencies
import org.amshove.kluent.shouldEqual
import org.junit.Test
import java.io.StringReader

/**
 * The unit tests related to parsing streams of characters. It is the main logic of whole coding challenge.
 */
class ReaderExtTest {
    @Test
    fun should_build_frequencies() {
        val words = "cat cat\n dog"
        val frequencies = StringReader(words).wordFrequencies()
        frequencies.getValue("cat") shouldEqual 2
        frequencies.getValue("dog") shouldEqual 1
    }

    @Test
    fun should_collect_every_10th() {
        val characters = "abcdefghijklmnopqrstuvwxyz".reader().allNthCharacters(9)

        characters shouldEqual listOf('j', 't')
    }

    @Test
    fun should_collect_every_2th() {
        val characters = "abcdefg".reader().allNthCharacters(1)

        characters shouldEqual listOf('b', 'd', 'f')
    }

    @Test
    fun test_collect_3d_character() {
        val char = "word cat".reader().firstNthCharacter(2)

        char shouldEqual 'r'
    }
}