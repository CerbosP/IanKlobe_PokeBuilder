package com.example.ianklobe_pokebuilder.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FlavorEffect(
    @SerializedName("flavor_text")
    val flavor: String,
    val language: LanguageOption
): Parcelable
