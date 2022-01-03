package com.mutualmobile.mmleave.data.service

import com.google.firebase.auth.FirebaseAuth
import com.mutualmobile.mmleave.data.model.MMUser

class FirebaseAuthenticationService : AuthenticationService {

    /**
     * Take the data and post it to the firebase
     */
    override fun registerUser() {
       FirebaseAuth.getInstance().signInWithCredential()
    }

    /**
     * This will return a Boolean value
     * We will check the Email exists in the DB or not
     * If yes then we will return true
     * Else we will return false and call the RegisterUser method
     */
    override fun isUserExists(email: String) : Boolean{
       return false
    }

}