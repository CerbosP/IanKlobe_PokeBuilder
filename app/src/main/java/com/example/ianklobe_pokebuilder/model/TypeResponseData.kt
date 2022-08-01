package com.example.ianklobe_pokebuilder.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TypeResponseData(
    val pokemon: PokeResponseData
): Parcelable
