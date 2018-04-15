@file:JvmName("ReaderUtils")

package com.tom.kata.coroutines.parser.domain

import android.support.v4.util.ArrayMap
import java.io.Reader

/**
 * Returns a sequence of corresponding characters from stream.
 *
 * *Note*: the caller must close the underlying `BufferedReader`
 */
fun Reader.asCharSequence(): Sequence<Char> =
    buffered().lineSequence().map { it.asSequence() }.flatten()

/**
 * Returns the sequence of n-th characters from stream.
 *
 * *Note*: the caller must close the underlying `BufferedReader`
 */
fun Reader.everyNthCharacter(n: Int): Sequence<Char> =
    asCharSequence().mapIndexed { index, char -> Pair(index, char) }
        .filter { it.first.inc() % n.inc() == 0 }
        .map { it.second }

/**
 * Returns the collection of n-th characters from stream.
 */
fun Reader.allNthCharacters(n: Int): List<Char> =
    use { it.everyNthCharacter(n).toList() }

/**
 * Returns the most first n-th character from stream.
 */
fun Reader.firstNthCharacter(n: Int): Char =
    use { it.everyNthCharacter(n).first() }

/**
 * Returns map of word/frequency from stream.
 */
fun Reader.wordFrequencies(): Map<String, Int> {
    /**
     * ArrayMap consumes less space, but suffers from performance hit. Because we are using it to build up frequency
     * table we are trading performance for memory efficiency.
     */
    val frequencies = ArrayMap<String, Int>()
    forEachLine { line ->
        line.split("\\s+".toRegex())
            .filter { !it.isEmpty() }
            .forEach {
                val frequency = frequencies.getOrPut(it, { 0 })
                frequencies[it] = frequency.inc()
            }
    }
    return frequencies
}