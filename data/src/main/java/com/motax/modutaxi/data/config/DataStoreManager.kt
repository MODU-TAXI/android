package com.motax.modutaxi.data.config

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.motax.modutaxi.data.Constants
import com.motax.modutaxi.data.Constants.ACCESS_TOKEN
import com.motax.modutaxi.data.Constants.REFRESH_TOKEN
import com.motax.modutaxi.data.repository.AuthRepositoryImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey(ACCESS_TOKEN)
        private val REFRESH_TOKEN_KEY = stringPreferencesKey(REFRESH_TOKEN)
        private val MEMBER_ID = longPreferencesKey(Constants.MEMBER_ID)
        private val GENDER = stringPreferencesKey(Constants.GENDER)
        private val PROFILE_IMG = stringPreferencesKey(Constants.PROFILE_IMG)
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

    suspend fun getMemberId(): Long? {
        return dataStore.data.map{ prefs ->
            prefs[MEMBER_ID]
        }.first()
    }

    suspend fun getGender(): String? {
        return dataStore.data.map{ prefs ->
            prefs[GENDER]
        }.first()
    }

    suspend fun getProfileImg(): String? {
        return dataStore.data.map { prefs ->
            prefs[PROFILE_IMG]
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

    suspend fun putMemberId(memberId: Long){
        dataStore.edit{ prefs ->
            prefs[MEMBER_ID] = memberId
        }
    }

    suspend fun putGender(gender: String){
        dataStore.edit { prefs ->
            prefs[GENDER] = gender
        }
    }

    suspend fun putProfileImg(profileImg: String) {
        dataStore.edit { prefs ->
            prefs[PROFILE_IMG] = profileImg
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

    suspend fun deleteMemberId(){
        dataStore.edit{ prefs ->
            prefs.remove(MEMBER_ID)
        }
    }

    suspend fun deleteGender(){
        dataStore.edit{ prefs ->
            prefs.remove(GENDER)
        }
    }

    suspend fun deleteProfileImg() {
        dataStore.edit { prefs ->
            prefs.remove(PROFILE_IMG)
        }
    }

}