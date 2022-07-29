package com.example.ianklobe_pokebuilder.api

import com.example.ianklobe_pokebuilder.model.PokeResponse
import com.example.ianklobe_pokebuilder.model.SinglePokeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

    @GET("pokemon/")
    suspend fun getPokemon(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<PokeResponse>

    @GET("pokemon/{id}")
    suspend fun getSinglePokemon(
        @Path("id") id: Int
    ): Response<SinglePokeResponse>

    @GET("pokemon/{name}")
    suspend fun getSinglePokemon(
        @Path("name") name: String
    ): Response<SinglePokeResponse>
}