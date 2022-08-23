package com.example.ianklobe_pokebuilder.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpecificAbilityResponseData(
    @SerializedName("effect_entries")
    val effect: List<Effect>,
    @SerializedName("flavor_text_entries")
    val flavor: List<FlavorEffect>,
    val pokemon: List<AbilityPokemon>,
    val name: String
) : Parcelable
