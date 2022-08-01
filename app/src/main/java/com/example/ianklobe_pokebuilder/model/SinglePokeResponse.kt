package com.example.ianklobe_pokebuilder.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SinglePokeResponse(
    val name: String,
    val sprites: PokeSprite,
    val stats: List<Stats>,
    val height: Int,
    val weight: Int
): Parcelable
