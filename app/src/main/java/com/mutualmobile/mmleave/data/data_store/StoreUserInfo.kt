package com.mutualmobile.mmleave.data.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StoreUserInfo @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private val Context.authDataStore: DataStore<Preferences> by preferencesDataStore("auth_info")
        val IS_USER_AUTHENTICATE = booleanPreferencesKey("is_user_authenticate")
        val IS_USER_ADMIN = booleanPreferencesKey("is_user_admin")
        val USER_TOTAL_PTO_LEFT = intPreferencesKey("user_total_pto_left")
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

    val getUserTotalPto: Flow<Int> = context.authDataStore.data
        .map { pref ->
            pref[USER_TOTAL_PTO_LEFT] ?: 0
        }

    suspend fun setUserTotalPto(totalPtoLeavesLeft : Int){
        context.authDataStore.edit { pref ->
            pref[USER_TOTAL_PTO_LEFT] = totalPtoLeavesLeft
        }
    }

    val getIsUserAdminState : Flow<Boolean?> = context.authDataStore.data
        .map { pref ->
            pref[IS_USER_ADMIN] ?: false
        }

    suspend fun setIsUserAdminState(isAdmin : Boolean){
        context.authDataStore.edit { pref ->
            pref[IS_USER_ADMIN] = isAdmin
        }
    }
}