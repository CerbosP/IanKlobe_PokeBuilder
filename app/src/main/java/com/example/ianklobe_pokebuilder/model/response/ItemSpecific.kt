package com.example.ianklobe_pokebuilder.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemSpecific(
    val attributes: List<ItemAttributes>,
    val cost: Int,
    @SerializedName("effect_entries")
    val effect: List<Effect>,
    @SerializedName("fling_effect")
    val flingEffect: Fling?,
    @SerializedName("fling_power")
    val flingPower: Int?,
    val name: String,
    @SerializedName("held_by_pokemon")
    val heldBy: List<PokeHeld>,
    val sprites: ItemSprite
): Parcelable
