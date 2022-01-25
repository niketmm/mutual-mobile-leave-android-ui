package com.mutualmobile.mmleave.feature_auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutualmobile.mmleave.data.data_store.StoreUserInfo
import com.mutualmobile.mmleave.feature_auth.domain.usecase.AuthenticateUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authenticateUseCases: AuthenticateUseCases,
    private val storeUserInfo: StoreUserInfo
) : ViewModel() {

    init {
        isAuthenticated()
    }

    private val _authUiFlow = MutableSharedFlow<AuthUiEvents>()
    val authUiFlow: SharedFlow<AuthUiEvents> = _authUiFlow

    private val _userAuthState = MutableSharedFlow<Boolean>(0)
    val userAuthState = _userAuthState.asSharedFlow()

    private val _isUserAuthenticated = MutableStateFlow(false)
    val isUserAuthenticated = _isUserAuthenticated.asStateFlow()

    fun getUserAuthState() {
        viewModelScope.launch {
            storeUserInfo.getUserAuthenticateState.collect {
                _userAuthState.emit(it)
                _isUserAuthenticated.value = it
            }
        }
    }

    private fun isAuthenticated() {
        viewModelScope.launch {
            // To keep Splash Icon stable for 2 Seconds
            delay(2000)
            storeUserInfo.getUserAuthenticateState.collect {
                _isUserAuthenticated.value = it
            }
        }
    }

    fun onEvents(authEvent : LoginEvents){
        when(authEvent){
            is LoginEvents.HitSignInButton -> {
                viewModelScope.launch {
                    try {
                        val authCredential = authenticateUseCases.signInUserUseCase(authEvent.intent)

                        val authResult = authenticateUseCases.googleAuthenticateUserUseCase(authCredential = authCredential)

                        if (authResult.user != null) {
                            _authUiFlow.emit(AuthUiEvents.ProceedToHomeScreen)
                            storeUserInfo.setUserAuthenticateState(true)
                            storeUserInfo.setUserTotalPto(24)
                        } else {
                            _authUiFlow.emit(AuthUiEvents.ShowFailureSnackBar("Failed to authenticate, try again"))
                        }
                    } catch (e: Exception) {
                        _authUiFlow.emit(AuthUiEvents.ShowFailureSnackBar(e.message.toString()))
                    }
                }
            }
        }
    }
}