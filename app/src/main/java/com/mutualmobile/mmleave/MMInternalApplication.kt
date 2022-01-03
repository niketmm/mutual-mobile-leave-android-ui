package com.mutualmobile.mmleave

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MMInternalApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}