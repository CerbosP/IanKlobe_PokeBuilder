package com.example.ianklobe_pokebuilder.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AbilityPokemon(
    val slot: Int,
    val pokemon: PokeResponseData
): Parcelable
