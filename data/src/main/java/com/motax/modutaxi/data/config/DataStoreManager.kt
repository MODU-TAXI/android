package com.motax.modutaxi.data.config

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.motax.modutaxi.data.Constants.ACCESS_TOKEN
import com.motax.modutaxi.data.Constants.REFRESH_TOKEN
import com.motax.modutaxi.data.Constants.MEMBER_ID
import com.motax.modutaxi.data.Constants.MEMBER_NAME
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey(ACCESS_TOKEN)
        private val REFRESH_TOKEN_KEY = stringPreferencesKey(REFRESH_TOKEN)
        private val MEMBER_ID_KEY = stringPreferencesKey(MEMBER_ID)
        private val MEMBER_NAME_KEY = stringPreferencesKey(MEMBER_NAME)
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
    suspend fun getMemberId(): String? {
        return dataStore.data.map { prefs ->
            prefs[MEMBER_ID_KEY]
        }.first()
    }

    suspend fun getMemberName(): String? {
        return dataStore.data.map { prefs ->
            prefs[MEMBER_NAME_KEY]
        }.first()
    }

    suspend fun putMemberId(id: String) {
        dataStore.edit { prefs ->
            prefs[MEMBER_ID_KEY] = id
        }
    }

    suspend fun putMemberName(name: String) {
        dataStore.edit { prefs ->
            prefs[MEMBER_NAME_KEY] = name
        }
    }

    suspend fun deleteMemberId() {
        dataStore.edit { prefs ->
            prefs.remove(MEMBER_ID_KEY)
        }
    }

    suspend fun deleteMemberName() {
        dataStore.edit { prefs ->
            prefs.remove(MEMBER_NAME_KEY)
        }
    }
}