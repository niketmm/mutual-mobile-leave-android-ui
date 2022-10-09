package com.mutualmobile.mmleave

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication


class HiltTestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {

        /**
         * `HiltTestRunnerApplication::class.java.name` is a String
         * `HiltTestRunnerApplication::class` is a class provided by Hilt to Mock
         *  our Application class
         */

        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}