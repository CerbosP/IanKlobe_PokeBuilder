package com.example.ianklobe_pokebuilder.api

import com.example.ianklobe_pokebuilder.model.response.PokeResponse
import com.example.ianklobe_pokebuilder.model.response.SinglePokeResponse
import com.example.ianklobe_pokebuilder.model.response.TypeResponse
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

    @GET("type/{name}")
    suspend fun getPokemonByType(
        @Path("name") type: String
    ): Response<TypeResponse>
}