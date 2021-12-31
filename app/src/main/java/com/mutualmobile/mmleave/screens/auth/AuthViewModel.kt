package com.mutualmobile.mmleave.screens.auth

import androidx.lifecycle.ViewModel
import com.mutualmobile.mmleave.data.repo.AuthRepo
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authRepo: AuthRepo
) : ViewModel() {

    fun authUser() : Boolean {
        // Firebase Auth
        return true
    }

    override fun onCleared() {
        super.onCleared()
        // Reset the states here
    }
}