package com.example.ianklobe_pokebuilder.utils

import java.util.*

fun String.getPicUrl(): String {
    val id = this.extractId()
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"
}

fun String.getPicUrlShiny(): String {
    val id = this.extractId()
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/${id}.png"
}

fun String.extractId() = this.substringAfter("pokemon").replace("/", "").toInt()

fun String.format(): String {
    return split("-").joinToString(" ") { itOne ->
        itOne.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault())
            else it.toString()
        }
    }
}