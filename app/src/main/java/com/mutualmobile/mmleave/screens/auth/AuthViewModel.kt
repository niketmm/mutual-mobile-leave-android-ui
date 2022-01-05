package com.mutualmobile.mmleave.screens.auth

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutualmobile.mmleave.services.auth.firebase.AuthenticationService
import com.mutualmobile.mmleave.services.auth.social.GoogleSocialService
import com.mutualmobile.mmleave.util.LandingPageState
import com.mutualmobile.mmleave.util.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
  private val socialService: GoogleSocialService,
  private val authenticationService: AuthenticationService
) :
    ViewModel() {

//  private var _loadingState = MutableStateFlow(LoadingState.IDLE)
//  val loadingState = _loadingState

  private val _authFlow = MutableSharedFlow<LandingPageState<String>>()
  val authFlow: SharedFlow<LandingPageState<String>> = _authFlow

  fun handleGoogleSignInResult(data: Intent) {
    viewModelScope.launch {
      try {
        _authFlow.emit(LandingPageState.loading())

        val authCredential = socialService.signIn(data)

        val authResult = authenticationService.authenticateUser(authCredential = authCredential)

        if (authResult.user != null){
          _authFlow.emit(LandingPageState.success("Logged In Successfully"))
        }else {
          _authFlow.emit(LandingPageState.failed("Exception thrown: "))
        }


      } catch (e: Exception) {
        _authFlow.emit(LandingPageState.failed(e.message.toString()))
      }
    }
  }
}