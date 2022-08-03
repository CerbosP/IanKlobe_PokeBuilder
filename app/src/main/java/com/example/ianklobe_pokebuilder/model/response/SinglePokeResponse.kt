package com.example.ianklobe_pokebuilder.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SinglePokeResponse(
    val name: String,
    val sprites: PokeSprite,
    val stats: List<Stats>,
    val height: Int,
    val weight: Int,
    val types: List<PokeTypes>,
    val abilities: List<PokeAbilities>
): Parcelable
