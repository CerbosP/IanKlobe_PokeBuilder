package com.example.ianklobe_pokebuilder.utils

fun String.getPicUrl(): String {
    val id = this.extractId()
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"
}

fun String.getPicUrlShiny(): String {
    val id =  this.extractId()
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/${id}.png"
}

fun String.extractId() = this.substringAfter("pokemon").replace("/", "").toInt()
fun String.extractIdShiny() = this.substringAfter("shiny").replace("/", "").toInt()