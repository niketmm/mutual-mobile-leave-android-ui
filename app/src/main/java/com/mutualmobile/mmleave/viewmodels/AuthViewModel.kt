package com.mutualmobile.mmleave.viewmodels

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutualmobile.mmleave.services.auth.firebase.AuthenticationService
import com.mutualmobile.mmleave.services.auth.social.GoogleSocialService
import com.mutualmobile.mmleave.data.data_state.LandingPageState
import com.mutualmobile.mmleave.data.data_store.StoreUserInfo
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
    private val socialService: GoogleSocialService,
    private val authenticationService: AuthenticationService,
    private val storeUserInfo: StoreUserInfo
) : ViewModel() {

    init {
        isAuthenticated()
    }

    private val _authFlow = MutableSharedFlow<LandingPageState<String>>()
    val authFlow: SharedFlow<LandingPageState<String>> = _authFlow

    private val _userAuthState = MutableSharedFlow<Boolean>(0)
    val userAuthState = _userAuthState.asSharedFlow()

    private val _isUserAuthenticated = MutableStateFlow(false)
    val isUserAuthenticated = _isUserAuthenticated.asStateFlow()

    fun handleGoogleSignInResult(data: Intent) {
        viewModelScope.launch {
            try {
                _authFlow.emit(LandingPageState.loading())

                val authCredential = socialService.signIn(data)

                val authResult =
                    authenticationService.authenticateUser(authCredential = authCredential)

                if (authResult.user != null) {
                    _authFlow.emit(LandingPageState.success("Logged In Successfully"))
                    storeUserInfo.setUserAuthenticateState(true)
                    storeUserInfo.setUserTotalPto(24)
                } else {
                    _authFlow.emit(LandingPageState.failed("Exception thrown: "))
                }
            } catch (e: Exception) {
                _authFlow.emit(LandingPageState.failed(e.message.toString()))
            }
        }
    }

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
}