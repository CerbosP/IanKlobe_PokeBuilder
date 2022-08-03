package com.example.ianklobe_pokebuilder.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokeAbilities(
    val ability: PokeAbilitySpecific,
    @SerializedName("is_hidden")
    val hiddenAbility: Boolean,
    val slot: Int
):Parcelable
