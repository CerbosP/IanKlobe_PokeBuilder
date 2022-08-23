package com.example.ianklobe_pokebuilder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ianklobe_pokebuilder.api.PokeRepository
import com.example.ianklobe_pokebuilder.model.states.AccountStatus
import com.example.ianklobe_pokebuilder.model.states.UIState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

const val TAG = "PokeViewModel"

@HiltViewModel
class PokeViewModel @Inject constructor(
    private val repository: PokeRepository,
    private val dispatcher: CoroutineDispatcher,
    private val firebase: FirebaseAuth
): ViewModel() {

    private val viewModelSafeScope by lazy {
        viewModelScope + coroutineExceptionHandler
    }

    // For logging errors of the coroutine
    private val coroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e(
                TAG,
                "Context: $coroutineContext\nMessage: ${throwable.localizedMessage}",
                throwable
            )
        }
    }

    private val _pokeList = MutableLiveData<UIState>()
    val pokeList: LiveData<UIState> get() = _pokeList

    private val _pokeTypeList = MutableLiveData<UIState>()
    val pokeTypeList: LiveData<UIState> get() = _pokeTypeList

    private val _pokeDetails = MutableLiveData<UIState>()
    val pokeDetails: LiveData<UIState> get() = _pokeDetails

    private val _pokeEggList = MutableLiveData<UIState>()
    val pokeEgg: LiveData<UIState> get() = _pokeEggList

    private val _abilityList = MutableLiveData<UIState>()
    val abilityList: LiveData<UIState> get() = _abilityList

    private val _abilityDetail = MutableLiveData<UIState>()
    val abilityDetail: LiveData<UIState> get() = _abilityDetail

    private val _itemList = MutableLiveData<UIState>()
    val itemList: LiveData<UIState> get() = _itemList

    private val _itemCategoryList = MutableLiveData<UIState>()
    val itemCategoryList: LiveData<UIState> get() = _itemCategoryList

    private val _itemDetail = MutableLiveData<UIState>()
    val itemDetail: LiveData<UIState> get() = _itemDetail

    private val _accountStatus = MutableLiveData<AccountStatus>()
    val accountStatus: LiveData<AccountStatus>
        get() = _accountStatus

    private val _currentTrainer = MutableLiveData<FirebaseUser?>()
    val currentTrainer: LiveData<FirebaseUser?>
        get() = _currentTrainer

    fun getPokemon(limit: Int, offset: Int) {
        viewModelSafeScope.launch(dispatcher) {
            repository.getPokemon(limit, offset).collect {
                _pokeList.postValue(it)
            }
        }
    }

    fun getPokemonByType(type: String) {
        viewModelSafeScope.launch {
            repository.getPokemonByType(type).collect {
                _pokeTypeList.postValue(it)
            }
        }
    }

    fun getSinglePokemon(name: String) {
        viewModelSafeScope.launch {
            repository.getSinglePokemon(name).collect {
                _pokeDetails.postValue(it)
            }
        }
    }

    fun getEggGroup(group: String) {
        viewModelSafeScope.launch {
            repository.getEggGroup(group).collect {
                _pokeEggList.postValue(it)
            }
        }
    }

    fun getAbility() {
        viewModelSafeScope.launch {
            repository.getAbility().collect {
                _abilityList.postValue(it)
            }
        }
    }

    fun getSingleAbility(ability: String) {
        viewModelSafeScope.launch {
            repository.getSingleAbility(ability).collect() {
                _abilityDetail.postValue(it)
            }
        }
    }

    fun getItemCategory() {
        viewModelSafeScope.launch {
            repository.getItemCategory().collect {
                _itemCategoryList.postValue(it)
            }
        }
    }

    fun getItems(category: String) {
        viewModelSafeScope.launch {
            repository.getItems(category).collect {
                _itemList.postValue(it)
            }
        }
    }

    fun getSingleItem(item: String) {
        viewModelSafeScope.launch {
            repository.getSingleItem(item).collect {
                _itemDetail.postValue(it)
            }
        }
    }

    /*
        Using these set functions to start the opening fragments in the loading state.
        This way they the api is only called when the fragment is first opened.
     */
    fun setPokeLoadingState() {
        _pokeList.value = UIState.Loading
    }

    fun setAbilityLoadingState() {
        _abilityList.value = UIState.Loading
    }

    fun setAbilityDetailLoadingState() {
        _abilityDetail.value = UIState.Loading
    }

    fun setTypeLoadingState() {
        _pokeTypeList.value = UIState.Loading
    }

    fun setEggLoadingState() {
        _pokeEggList.value = UIState.Loading
    }

    fun setDetailLoadingState() {
        _pokeDetails.value = UIState.Loading
    }

    fun setItemLoadingState() {
        _itemList.value = UIState.Loading
    }

    fun setItemCategoryLoadingState() {
        _itemCategoryList.value = UIState.Loading
    }

    fun setItemDetailLoadingState() {
        _itemDetail.value = UIState.Loading
    }

    fun createTrainer(email: String, password: String) {
        firebase.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _currentTrainer.postValue(firebase.currentUser)
                    _accountStatus.postValue(AccountStatus.SUBMITTED)
                } else if (task.exception is FirebaseAuthUserCollisionException) {
                    _accountStatus.postValue(AccountStatus.EMAIL_EXISTS)
                } else {
                    _accountStatus.postValue(AccountStatus.CREATION_ERROR)
                }
            }
    }

    fun signIn(email: String, password: String) {
        firebase.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _currentTrainer.postValue(firebase.currentUser)
                    _accountStatus.postValue(AccountStatus.SIGNED_IN)
                } else {
                    _accountStatus.postValue(AccountStatus.SIGN_IN_ERROR)
                }
            }
    }

    fun signed() {
        _accountStatus.postValue(AccountStatus.EXISTS)
    }

    fun logout() {
        _accountStatus.postValue(AccountStatus.SIGNED_OUT)
        firebase.signOut()
        _currentTrainer.value = null
    }
}