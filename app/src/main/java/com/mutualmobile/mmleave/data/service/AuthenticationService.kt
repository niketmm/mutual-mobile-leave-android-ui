package com.mutualmobile.mmleave.data.service

import com.google.firebase.auth.AuthCredential
import com.mutualmobile.mmleave.data.model.MMUser

interface AuthenticationService {
    fun registerUser()
    fun isUserExists(email : String) : Boolean
}