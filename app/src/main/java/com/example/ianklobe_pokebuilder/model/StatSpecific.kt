package com.example.ianklobe_pokebuilder.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StatSpecific(
    val name: String
): Parcelable
