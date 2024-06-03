package com.motax.modutaxi.data.config

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.motax.modutaxi.data.Constants.ACCESS_TOKEN
import com.motax.modutaxi.data.Constants.AUTO_LOGIN
import com.motax.modutaxi.data.Constants.REFRESH_TOKEN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey(ACCESS_TOKEN)
        private val REFRESH_TOKEN_KEY = stringPreferencesKey(REFRESH_TOKEN)
    }

    suspend fun getAccessToken(): String? {
        return dataStore.data.map { prefs ->
            prefs[ACCESS_TOKEN_KEY]
        }.first()
    }

    suspend fun getRefreshToken() : String? {
        return dataStore.data.map { prefs ->
            prefs[REFRESH_TOKEN_KEY]
        }.first()
    }

    suspend fun putAccessToken(token : String){
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN_KEY] = token
        }
    }

    suspend fun putRefreshToken(token : String){
        dataStore.edit { prefs ->
            prefs[REFRESH_TOKEN_KEY] = token
        }
    }

    suspend fun deleteAccessToken(){
        dataStore.edit { prefs ->
            prefs.remove(ACCESS_TOKEN_KEY)
        }
    }

    suspend fun deleteRefreshToken(){
        dataStore.edit { prefs ->
            prefs.remove(REFRESH_TOKEN_KEY)
        }
    }

}