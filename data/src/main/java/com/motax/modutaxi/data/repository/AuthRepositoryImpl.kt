package com.motax.modutaxi.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.motax.modutaxi.data.Constants
import com.motax.modutaxi.data.model.mapper.toDomain
import com.motax.modutaxi.data.remote.AuthApi
import com.motax.modutaxi.domain.model.AuthData
import com.motax.modutaxi.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
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
        private val MEMBER_ID_KEY = stringPreferencesKey(Constants.MEMBER_ID)
        private val MEMBER_NAME_KEY = stringPreferencesKey(Constants.MEMBER_NAME)
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

    override suspend fun getMemberId(): String? {
        return dataStore.data.map { prefs ->
            prefs[MEMBER_ID_KEY]
        }.first()
    }

    override suspend fun getMemberName(): String? {
        return dataStore.data.map { prefs ->
            prefs[MEMBER_NAME_KEY]
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

    override suspend fun putMemberId(id: String) {
        dataStore.edit { prefs ->
            prefs[MEMBER_ID_KEY] = id
        }
    }

    override suspend fun putMemberName(name: String) {
        dataStore.edit { prefs ->
            prefs[MEMBER_NAME_KEY] = name
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
            prefs.remove(MEMBER_ID_KEY)
        }
    }

    override suspend fun deleteMemberName() {
        dataStore.edit { prefs ->
            prefs.remove(MEMBER_NAME_KEY)
        }
    }

    override suspend fun refreshToken(refreshToken: String): Result<AuthData> = runCatching {
        api.refreshToken(refreshToken)
    }.mapCatching { it.toDomain() }
}