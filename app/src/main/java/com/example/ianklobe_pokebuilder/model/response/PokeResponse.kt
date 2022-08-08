package com.example.ianklobe_pokebuilder.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokeResponse (
    val results: List<PokeResponseData>
): Parcelable