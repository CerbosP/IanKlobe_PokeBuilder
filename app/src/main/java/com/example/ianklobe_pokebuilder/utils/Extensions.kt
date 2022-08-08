package com.example.ianklobe_pokebuilder.utils

import android.content.Context
import androidx.lifecycle.Transformations.map
import com.example.ianklobe_pokebuilder.R
import com.example.ianklobe_pokebuilder.model.response.PokeResponse
import com.example.ianklobe_pokebuilder.model.response.SinglePokeResponse
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

fun PokeResponse.toPokeList(context: Context): List<String> {
    return this.results.map { it.name.formatName() }
}