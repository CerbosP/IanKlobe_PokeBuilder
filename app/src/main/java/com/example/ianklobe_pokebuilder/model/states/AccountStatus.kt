package com.example.ianklobe_pokebuilder.model.states

enum class AccountStatus {
    EXISTS,
    SUBMITTED,
    CANCELED,
    CREATION_ERROR,
    SIGN_IN_ERROR,
    EMAIL_EXISTS,
    USERNAME_EXISTS,
    SIGNED_IN,
    SIGNED_OUT
}