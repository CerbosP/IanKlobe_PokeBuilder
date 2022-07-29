package com.example.ianklobe_pokebuilder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ianklobe_pokebuilder.api.PokeRepository
import com.example.ianklobe_pokebuilder.model.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

const val TAG = "PokeViewModel"

@HiltViewModel
class PokeViewModel @Inject constructor(
    private val repository: PokeRepository,
    private val dispatcher: CoroutineDispatcher
): ViewModel() {

    private val viewModelSafeScope by lazy {
        viewModelScope + coroutineExceptionHandler
    }

    // For logging errors of the coroutine
    private val coroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e(TAG, "Context: $coroutineContext\nMessage: ${throwable.localizedMessage}", throwable)
        }
    }

    private val _pokeList = MutableLiveData<UIState>()
    val pokeList: LiveData<UIState> get() = _pokeList

    private val _pokeSpeList = MutableLiveData<UIState>()
    val pokeSpeList: LiveData<UIState> get() = _pokeSpeList

    private val _pokeDetails = MutableLiveData<UIState>()
    val pokeDetails: LiveData<UIState> get() = _pokeDetails

    fun getPokemon(limit: Int, offset: Int) {
        viewModelSafeScope.launch(dispatcher) {
            repository.getPokemon(limit, offset).collect {
                _pokeList.postValue(it)
            }
        }
    }

    fun getSinglePokemon(id: Int) {
        viewModelScope.launch {
            repository.getSinglePokemon(id).collect {
                _pokeSpeList.postValue(it)
            }
        }
    }

    fun getSinglePokemon(name: String) {
        viewModelScope.launch {
            repository.getSinglePokemon(name).collect {
                _pokeSpeList.postValue(it)
            }
        }
    }

    /*
        Using these set functions to start the opening fragments in the loading state.
        This way they the api is only called when the fragment is first opened.
     */
    fun setLoadingState() { _pokeList.value = UIState.Loading }
}