package com.example.ianklobe_pokebuilder.viewmodel

import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ianklobe_pokebuilder.model.states.AccountStatus
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.plus
import javax.inject.Inject
import kotlin.coroutines.coroutineContext


const val TrainerTag = "TrainerViewModel"

@HiltViewModel
class TrainerViewModel @Inject constructor(
    private val firebase: FirebaseAuth
): ViewModel() {

    private val _accountStatus = MutableLiveData<AccountStatus>()
    val accountStatus: LiveData<AccountStatus>
        get() = _accountStatus

    private val _currentTrainer = MutableLiveData<FirebaseUser?>()
    val currentTrainer: LiveData<FirebaseUser?>
        get() = _currentTrainer

    private val viewModelSafeScope by lazy {
        viewModelScope + coroutineExceptionHandler
    }

    // For logging errors of the coroutine
    private val coroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e(TAG,
                "Context: $coroutineContext\nMessage: ${throwable.localizedMessage}",
                throwable)
        }
    }

    fun createTrainer(email: String, password: String) {
        firebase.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    _currentTrainer.postValue(firebase.currentUser)
                    _accountStatus.postValue(AccountStatus.SUBMITTED)
                } else if(task.exception is FirebaseAuthUserCollisionException) {
                    _accountStatus.postValue(AccountStatus.EMAIL_EXISTS)
                } else {
                    _accountStatus.postValue(AccountStatus.CREATION_ERROR)
                }
            }
    }

    fun signIn(email: String, password: String) {
        firebase.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
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
