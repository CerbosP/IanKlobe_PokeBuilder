package com.example.ianklobe_pokebuilder.utils

import com.example.ianklobe_pokebuilder.model.response.PokeResponse
import java.util.*

fun String.getPicUrl(shiny: Boolean): String {
    val id = this.extractId()
    return if(shiny) "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/${id}.png"
    else "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"
}

fun String.extractId(): Int {
    if(this.contains("pokemon-species")) {
        return this.substringAfter("pokemon-species").replace("/", "").toInt()
    } else {
        return this.substringAfter("pokemon").replace("/", "").toInt()
    }
}

fun String.formatName(): String {
    return split("-").joinToString(" ") { itOne ->
        itOne.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault())
            else it.toString()
        }
    }
}

fun String.deformatName(): String {
    return split(" ").joinToString("-") { itOne ->
        itOne.lowercase().trim()
    }
}

fun PokeResponse.toPokeList(): List<String> {
    return this.results.map { it.name.formatName() }
}

fun Int.convertHeight(): Int {
    var newHeight: Double = this.toDouble()
    newHeight *= 3.937
    newHeight += 0.5
    return newHeight.toInt()
}

fun Int.convertWeight(): Double {
    var newWeight: Double = this.toDouble()
    newWeight *= .2205
    return newWeight
}