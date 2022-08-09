package com.example.ianklobe_pokebuilder

import com.example.ianklobe_pokebuilder.utils.*
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun height_isCorrect() {
        var height = 4

        assertEquals(16, height.convertHeight())
    }

    @Test
    fun weight_isCorrect() {
        var weight = 60

        assertEquals(13.23, weight.convertWeight(), 0.0)
    }

    @Test
    fun extractId_isCorrect() {
        val url = "https://pokeapi.co/api/v2/pokemon/25/"

        assertEquals(25, url.extractId())
    }

    @Test
    fun formatName_isCorrect() {
        val name = "mr-mime"

        assertEquals("Mr Mime", name.formatName())
    }

    @Test
    fun deformatName_isCorrect() {
        val name = "Mr Mime"

        assertEquals("mr-mime", name.deformatName())
    }
}