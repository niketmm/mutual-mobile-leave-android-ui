package com.mutualmobile.mmleave.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StoreUserInfo @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private val Context.authDataStore : DataStore<Preferences> by preferencesDataStore("auth_info")
        val IS_USER_AUTHENTICATE = booleanPreferencesKey("is_user_authenticate")
    }

    val getUserAuthenticateState: Flow<Boolean> = context.authDataStore.data
        .map { pref ->
            pref[IS_USER_AUTHENTICATE] ?: false
        }

    suspend fun setUserAuthenticateState(state: Boolean) {
        context.authDataStore.edit { pref ->
            pref[IS_USER_AUTHENTICATE] = state
        }
    }
}