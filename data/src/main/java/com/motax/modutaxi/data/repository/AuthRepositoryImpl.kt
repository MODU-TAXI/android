package com.motax.modutaxi.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.motax.modutaxi.data.Constants
import com.motax.modutaxi.data.model.mapper.toDomain
import com.motax.modutaxi.data.remote.AuthApi
import com.motax.modutaxi.domain.model.AuthData
import com.motax.modutaxi.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val api: AuthApi
) : AuthRepository {

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey(Constants.ACCESS_TOKEN)
        private val REFRESH_TOKEN_KEY = stringPreferencesKey(Constants.REFRESH_TOKEN)
        private val MEMBER_ID = longPreferencesKey(Constants.MEMBER_ID)
        private val GENDER = stringPreferencesKey(Constants.GENDER)
        private val PROFILE_IMG = stringPreferencesKey(Constants.PROFILE_IMG)
    }

    override suspend fun getAccessToken(): String? {
        return dataStore.data.map { prefs ->
            prefs[ACCESS_TOKEN_KEY]
        }.first()
    }

    override suspend fun getRefreshToken(): String? {
        return dataStore.data.map { prefs ->
            prefs[REFRESH_TOKEN_KEY]
        }.first()
    }

    override suspend fun getMemberId(): Long? {
        return dataStore.data.map { prefs ->
            prefs[MEMBER_ID]
        }.first()
    }

    override suspend fun getGender(): String? {
        return dataStore.data.map { prefs ->
            prefs[GENDER]
        }.first()
    }

    override suspend fun getProfileImg(): String? {
        return dataStore.data.map { prefs ->
            prefs[PROFILE_IMG]
        }.first()
    }

    override suspend fun putAccessToken(token: String) {
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN_KEY] = token
        }
    }

    override suspend fun putRefreshToken(token: String) {
        dataStore.edit { prefs ->
            prefs[REFRESH_TOKEN_KEY] = token
        }
    }

    override suspend fun putMemberId(memberId: Long) {
        dataStore.edit { prefs ->
            prefs[MEMBER_ID] = memberId
        }
    }

    override suspend fun putGender(gender: String) {
        dataStore.edit { prefs ->
            prefs[GENDER] = gender
        }
    }

    override suspend fun putProfileImg(profileImg: String) {
        dataStore.edit { prefs ->
            prefs[PROFILE_IMG] = profileImg
        }
    }

    override suspend fun deleteAccessToken() {
        dataStore.edit { prefs ->
            prefs.remove(ACCESS_TOKEN_KEY)
        }
    }

    override suspend fun deleteRefreshToken() {
        dataStore.edit { prefs ->
            prefs.remove(REFRESH_TOKEN_KEY)
        }
    }

    override suspend fun deleteMemberId() {
        dataStore.edit { prefs ->
            prefs.remove(MEMBER_ID)
        }
    }

    override suspend fun deleteGender() {
        dataStore.edit { prefs ->
            prefs.remove(GENDER)
        }
    }

    override suspend fun deleteProfileImg() {
        dataStore.edit { prefs ->
            prefs.remove(PROFILE_IMG)
        }
    }

    override suspend fun refreshToken(refreshToken: String): Result<AuthData> = runCatching {
        api.refreshToken(refreshToken)
    }.mapCatching { it.toDomain() }
}