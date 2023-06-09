package com.agrafast.ui.screen.authetication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GoogleAuthViewModel : ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _state.update {
            it.copy(
                isSignInSuccesful = result.data != null,
                signInError = result.errorMessage
            )

        }
    }

    fun resetState() {
        _state.update { SignInState() }
    }
}