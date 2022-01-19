package com.mutualmobile.mmleave.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.mutualmobile.mmleave.util.Constants

object FirebaseModule {

    val currentUser = FirebaseAuth.getInstance().currentUser?.email

    fun provideFirebaseUserCollectionReference(): CollectionReference {
        return FirebaseFirestore.getInstance()
            .collection(Constants.USERS_LIST_COLLECTION)
    }

    fun provideUserPtoRequestDocReference(): CollectionReference {
        val userEmail = FirebaseAuth.getInstance()
            .currentUser
            ?.email ?: " "

        return FirebaseFirestore.getInstance()
            .collection(Constants.USERS_LIST_COLLECTION)
            .document(userEmail)
            .collection(Constants.PTO_LIST_COLLECTION)
    }

    fun provideFirebaseNotificationCollectionReference(): CollectionReference {
        return FirebaseFirestore.getInstance()
            .collection(Constants.NOTIFICATION_COLLECTION)
    }

    fun provideFirebaseHolidayCollection() : CollectionReference {
        return FirebaseFirestore.getInstance()
            .collection(Constants.HOLIDAY_LIST_COLLECTION_FOR_22)
    }
}