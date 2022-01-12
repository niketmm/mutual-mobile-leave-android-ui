package com.mutualmobile.mmleave.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.mutualmobile.mmleave.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

object FirebaseModule {

    fun provideFirebaseUserCollectionReference() : CollectionReference {
        return FirebaseFirestore.getInstance()
            .collection(Constants.USERS_LIST_COLLECTION)
    }

    fun provideUserPtoRequestDocReference() : CollectionReference {
        val userEmail = FirebaseAuth.getInstance()
            .currentUser
            ?.email ?: " "

        return FirebaseFirestore.getInstance()
            .collection(Constants.USERS_LIST_COLLECTION)
            .document(userEmail)
            .collection(Constants.PTO_LIST_COLLECTION)
    }
}