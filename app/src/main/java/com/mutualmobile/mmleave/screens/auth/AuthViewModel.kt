package com.mutualmobile.mmleave.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.mutualmobile.mmleave.util.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.lang.Exception

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    private var _loadingState = MutableStateFlow(LoadingState.IDLE)
    val loadingState = _loadingState


    fun authUser(credential: AuthCredential) {
        viewModelScope.launch {
            try {
                loadingState.emit(LoadingState.LOADING)
                FirebaseAuth.getInstance().signInWithCredential(credential)
                loadingState.emit(LoadingState.LOADED)
            }catch (e: Exception){
                loadingState.emit(LoadingState.error(e.localizedMessage))
            }

        }
    }
}