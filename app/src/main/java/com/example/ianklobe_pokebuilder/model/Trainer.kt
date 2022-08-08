package com.example.ianklobe_pokebuilder.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Trainer(
    val username: String,
    val email: String,
    var password: String
):Parcelable
