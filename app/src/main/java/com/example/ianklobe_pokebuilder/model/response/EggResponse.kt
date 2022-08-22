package com.example.ianklobe_pokebuilder.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EggResponse(
    @SerializedName("pokemon_species")
    val pokemonSpecies: List<PokeResponseData>
): Parcelable
