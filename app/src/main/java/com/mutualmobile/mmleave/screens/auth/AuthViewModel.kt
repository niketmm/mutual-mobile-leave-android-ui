package com.mutualmobile.mmleave.screens.auth

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutualmobile.mmleave.services.auth.firebase.AuthenticationService
import com.mutualmobile.mmleave.services.auth.social.GoogleSocialService
import com.mutualmobile.mmleave.util.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
  private val socialService: GoogleSocialService,
  private val authenticationService: AuthenticationService
) :
    ViewModel() {

  private var _loadingState = MutableStateFlow(LoadingState.IDLE)
  val loadingState = _loadingState

  fun handleGoogleSignInResult(data: Intent) {
    viewModelScope.launch {

      try {
        loadingState.emit(LoadingState.LOADING)
        val authCredential = socialService.signIn(data)
        authenticationService.authenticateUser(authCredential = authCredential)
        loadingState.emit(LoadingState.LOADED)
      } catch (e: Exception) {
        loadingState.emit(LoadingState.error(e.localizedMessage))
      }

    }
  }
}