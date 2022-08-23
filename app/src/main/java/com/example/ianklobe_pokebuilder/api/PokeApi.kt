package com.example.ianklobe_pokebuilder.api

import com.example.ianklobe_pokebuilder.model.response.*
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

    @GET("pokemon/{name}")
    suspend fun getSinglePokemon(
        @Path("name") name: String
    ): Response<SinglePokeResponse>

    @GET("type/{name}")
    suspend fun getPokemonByType(
        @Path("name") type: String
    ): Response<TypeResponse>

    @GET("egg-group/{name}")
    suspend fun getEggGroup(
        @Path("name") group: String
    ): Response<EggResponse>

    @GET("ability?limit=327")
    suspend fun getAbility(): Response<AbilityResponse>

    @GET("item-category?limit=50/")
    suspend fun getItemCategory(): Response<ItemCategoryResponse>

    @GET("item-category/{name}")
    suspend fun getItems(
        @Path("name") category: String
    ): Response<ItemResponse>
}