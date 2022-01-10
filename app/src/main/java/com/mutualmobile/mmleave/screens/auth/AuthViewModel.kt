package com.mutualmobile.mmleave.screens.auth

import android.content.Intent
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutualmobile.mmleave.services.auth.firebase.AuthenticationService
import com.mutualmobile.mmleave.services.auth.social.GoogleSocialService
import com.mutualmobile.mmleave.util.LandingPageState
import com.mutualmobile.mmleave.util.StoreUserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val socialService: GoogleSocialService,
    private val authenticationService: AuthenticationService,
    private val storeUserInfo: StoreUserInfo
) : ViewModel() {

    private val _authFlow = MutableSharedFlow<LandingPageState<String>>()
    val authFlow: SharedFlow<LandingPageState<String>> = _authFlow

    private val _userAuthState = MutableStateFlow(false)
    val userAuthState = _userAuthState

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
            }
        }
    }
}