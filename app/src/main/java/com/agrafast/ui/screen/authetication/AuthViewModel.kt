package com.agrafast.ui.screen.authetication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agrafast.domain.FetchStatus
import com.agrafast.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import com.stevdzasan.onetap.rememberOneTapSignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _loginFlow = MutableStateFlow<FetchStatus<FirebaseUser>?>(null)
    val loginFlow: StateFlow<FetchStatus<FirebaseUser>?> = _loginFlow

    private val _signupFlow = MutableStateFlow<FetchStatus<FirebaseUser>?>(null)
    val signupFlow: StateFlow<FetchStatus<FirebaseUser>?> = _signupFlow

    val currentUser: FirebaseUser?
        get() = repository.currentUser

    init {
        if (repository.currentUser != null) {
            _loginFlow.value = FetchStatus.Success(repository.currentUser!!)
        }
    }

    fun loginUser(email: String, password: String) = viewModelScope.launch {
        _loginFlow.value = FetchStatus.Loading
        val result = repository.login(email, password)
        _loginFlow.value = result
    }

    fun signupUser(name: String, email: String, telpNumber:String, password: String) = viewModelScope.launch {
        _signupFlow.value = FetchStatus.Loading
        val result = repository.signup(name, email, telpNumber, password)
        _signupFlow.value = result
    }

    fun logout() {
        repository.logout()
        _loginFlow.value = null
        _signupFlow.value = null
    }


}