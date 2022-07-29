package com.example.ianklobe_pokebuilder.model

import com.google.gson.annotations.SerializedName

data class PokeSprite(
    @SerializedName("front_default")
    val frontDefault: String,
    @SerializedName("front_shiny")
    val frontShiny: String
)
