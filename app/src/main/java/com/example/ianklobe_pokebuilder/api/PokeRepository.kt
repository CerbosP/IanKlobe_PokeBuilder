package com.example.ianklobe_pokebuilder.api

import com.example.ianklobe_pokebuilder.model.states.UIState
import com.example.ianklobe_pokebuilder.utils.formatName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface PokeRepository {
    suspend fun getPokemon(limit: Int, offset: Int): Flow<UIState>
    suspend fun getSinglePokemon(name: String): Flow<UIState>
    suspend fun getPokemonByType(type: String): Flow<UIState>
}

class PokeRepositoryImpl @Inject constructor(private val pokeApi: PokeApi) : PokeRepository {
    override suspend fun getPokemon(limit: Int, offset: Int): Flow<UIState> =
        flow {
            try {
                val response = pokeApi.getPokemon(limit = limit, offset = offset)
                if (response.isSuccessful) {
                    emit(response.body()?.let {
                        UIState.Success(it)
                    } ?: throw Exception("Null Response"))
                } else {
                    throw Exception("Failed network call")
                }
            } catch (e: Exception) {
                emit(UIState.Error(e))
            }
        }

    override suspend fun getSinglePokemon(name: String): Flow<UIState> =
        flow {
            try {
                val response = pokeApi.getSinglePokemon(name = name)
                if (response.isSuccessful) {
                    emit(response.body()?.let {
                        UIState.Success(it)
                    } ?: throw Exception("Null Response"))
                } else {
                    throw Exception("Failure retrieving pokemon.\n${name.formatName()} ran away!")
                }
            } catch (e: Exception) {
                emit(UIState.Error(e))
            }
        }

    override suspend fun getPokemonByType(type: String): Flow<UIState> =
        flow {
            try {
                val response = pokeApi.getPokemonByType(type = type)
                if (response.isSuccessful) {
                    emit(response.body()?.let {
                        UIState.Success(it)
                    } ?: throw Exception("Null Response"))
                } else {
                    throw Exception("Failed network call")
                }
            } catch (e: Exception) {
                emit(UIState.Error(e))
            }
        }

}