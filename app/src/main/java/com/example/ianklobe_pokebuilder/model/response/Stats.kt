package com.example.ianklobe_pokebuilder.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Stats(
    @SerializedName("base_stat")
    val baseStat: Int,
    val stat: StatSpecific
): Parcelable
