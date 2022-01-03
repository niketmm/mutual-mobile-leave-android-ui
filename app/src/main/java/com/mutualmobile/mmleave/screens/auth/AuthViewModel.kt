package com.mutualmobile.mmleave.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.mutualmobile.mmleave.data.repo.AuthRepo
import com.mutualmobile.mmleave.util.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.io.IOException

class AuthViewModel @Inject constructor(
    private val authRepo: AuthRepo
) : ViewModel() {

    private var _loadingState = MutableStateFlow(LoadingState.IDLE)
    val loadingState = _loadingState


    fun authUser(credential: AuthCredential) : Boolean {
        viewModelScope.launch {
            try {
                loadingState.emit(LoadingState.LOADING)
                FirebaseAuth.getInstance().signInWithCredential()
            }catch (e: IOException){

            }

        }
        return false;
    }
}