package com.motax.modutaxi.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
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
        private val MEMBER_ID_KEY = longPreferencesKey(Constants.MEMBER_ID)
        private val MEMBER_NAME_KEY = stringPreferencesKey(Constants.MEMBER_NAME)
        private val MEMBER_NICKNAME_KEY = stringPreferencesKey(Constants.MEMBER_NICKNAME)
        private val MEMBER_GENDER_KEY = stringPreferencesKey(Constants.MEMBER_GENDER)
        private val MEMBER_PHONE_KEY = stringPreferencesKey(Constants.MEMBER_PHONE_NUMBER)
        private val MEMBER_EMAIL_KEY = stringPreferencesKey(Constants.MEMBER_EMAIL)
        private val MEMBER_MATCHING_COUNT_KEY = intPreferencesKey(Constants.MEMBER_MATCHING_COUNT)
        private val MEMBER_BLOCKED_KEY = booleanPreferencesKey(Constants.MEMBER_BLOCKED)
        private val PROFILE_URL_KEY = stringPreferencesKey(Constants.PROFILE_URL)
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
            prefs[MEMBER_ID_KEY]
        }.first()
    }

    override suspend fun getMemberName(): String? {
        return dataStore.data.map { prefs ->
            prefs[MEMBER_NAME_KEY]
        }.first()
    }

    override suspend fun getMemberGender(): String? {
        return dataStore.data.map { prefs ->
            prefs[MEMBER_GENDER_KEY]
        }.first()
    }

    override suspend fun getMemberPhoneNumber(): String? {
        return dataStore.data.map { prefs ->
            prefs[MEMBER_PHONE_KEY]
        }.first()
    }

    override suspend fun getMemberEmail(): String? {
        return dataStore.data.map { prefs ->
            prefs[MEMBER_EMAIL_KEY]
        }.first()
    }

    override suspend fun getMatchingCount(): Int? {
        return dataStore.data.map { prefs ->
            prefs[MEMBER_MATCHING_COUNT_KEY]
        }.first()
    }

    override suspend fun getMemberBlocked(): Boolean? {
        return dataStore.data.map { prefs ->
            prefs[MEMBER_BLOCKED_KEY]
        }.first()
    }

    override suspend fun getMemberNickName(): String? {
        return dataStore.data.map { prefs ->
            prefs[MEMBER_NICKNAME_KEY]
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

    override suspend fun putMemberId(id: Long) {
        dataStore.edit { prefs ->
            prefs[MEMBER_ID_KEY] = id
        }
    }

    override suspend fun putMemberName(name: String) {
        dataStore.edit { prefs ->
            prefs[MEMBER_NAME_KEY] = name
        }
    }

    override suspend fun putMemberGender(gender: String) {
        dataStore.edit { prefs ->
            prefs[MEMBER_GENDER_KEY] = gender
        }
    }

    override suspend fun putMemberPhoneNumber(phoneNumber: String) {
        dataStore.edit { prefs ->
            prefs[MEMBER_PHONE_KEY] = phoneNumber
        }
    }

    override suspend fun putMemberEmail(email: String) {
        dataStore.edit { prefs ->
            prefs[MEMBER_EMAIL_KEY] = email
        }
    }

    override suspend fun putMatchingCount(matchingCount: Int) {
        dataStore.edit { prefs ->
            prefs[MEMBER_MATCHING_COUNT_KEY] = matchingCount
        }
    }

    override suspend fun putMemberBlocked(blocked: Boolean) {
        dataStore.edit { prefs ->
            prefs[MEMBER_BLOCKED_KEY] = blocked
        }
    }

    override suspend fun putMemberNickName(nickName: String) {
        dataStore.edit { prefs ->
            prefs[MEMBER_NICKNAME_KEY] = nickName
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

    override suspend fun deleteMemberGender() {
        dataStore.edit { prefs ->
            prefs.remove(MEMBER_GENDER_KEY)
        }
    }

    override suspend fun deleteMemberPhoneNumber() {
        dataStore.edit { prefs ->
            prefs.remove(MEMBER_PHONE_KEY)
        }
    }

    override suspend fun deleteMemberEmail() {
        dataStore.edit { prefs ->
            prefs.remove(MEMBER_EMAIL_KEY)
        }
    }

    override suspend fun deleteMatchingCount() {
        dataStore.edit { prefs ->
            prefs.remove(MEMBER_MATCHING_COUNT_KEY)
        }
    }

    override suspend fun deleteMemberBlocked() {
        dataStore.edit { prefs ->
            prefs.remove(MEMBER_BLOCKED_KEY)
        }
    }

    override suspend fun deleteMemberNickName() {
        dataStore.edit { prefs ->
            prefs.remove(MEMBER_NICKNAME_KEY)
        }
    }

    override suspend fun refreshToken(refreshToken: String): Result<AuthData> = runCatching {
        api.refreshToken(refreshToken)
    }.mapCatching { it.toDomain() }


    override suspend fun putProfileUrl(url: String) {
        dataStore.edit { prefs ->
            prefs[PROFILE_URL_KEY] = url
        }
    }

    override suspend fun deleteProfileUrl() {
        dataStore.edit { prefs ->
            prefs.remove(PROFILE_URL_KEY)
        }
    }

    override suspend fun getProfileUrl(): String? {
        return dataStore.data.map { prefs ->
            prefs[PROFILE_URL_KEY]
        }.first()
    }
}