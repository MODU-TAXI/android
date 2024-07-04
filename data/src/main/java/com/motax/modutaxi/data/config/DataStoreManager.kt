package com.motax.modutaxi.data.config

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.motax.modutaxi.data.Constants
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey(Constants.ACCESS_TOKEN)
        private val REFRESH_TOKEN_KEY = stringPreferencesKey(Constants.REFRESH_TOKEN)
        private val MEMBER_ID_KEY = longPreferencesKey(Constants.MEMBER_ID)
        private val MEMBER_NAME_KEY = stringPreferencesKey(Constants.MEMBER_NAME)
        private val MEMBER_GENDER_KEY = stringPreferencesKey(Constants.MEMBER_GENDER)
        private val MEMBER_PHONE_KEY = stringPreferencesKey(Constants.MEMBER_PHONE_NUMBER)
        private val MEMBER_EMAIL_KEY = stringPreferencesKey(Constants.MEMBER_EMAIL)
        private val MEMBER_MATCHING_COUNT_KEY =
            stringPreferencesKey(Constants.MEMBER_MATCHING_COUNT)
        private val MEMBER_BLOCKED_KEY = stringPreferencesKey(Constants.MEMBER_BLOCKED)
        private val PROFILE_URL_KEY = stringPreferencesKey(Constants.PROFILE_URL)
    }

    suspend fun getAccessToken(): String? {
        return dataStore.data.map { prefs ->
            prefs[ACCESS_TOKEN_KEY]
        }.first()
    }

    suspend fun getRefreshToken(): String? {
        return dataStore.data.map { prefs ->
            prefs[REFRESH_TOKEN_KEY]
        }.first()
    }

    suspend fun getMemberId(): Long? {
        return dataStore.data.map { prefs ->
            prefs[MEMBER_ID_KEY]
        }.first()
    }

    suspend fun getMemberName(): String? {
        return dataStore.data.map { prefs ->
            prefs[MEMBER_NAME_KEY]
        }.first()
    }

    suspend fun getMemberGender(): String? {
        return dataStore.data.map { prefs ->
            prefs[MEMBER_GENDER_KEY]
        }.first()
    }

    suspend fun getMemberPhoneNumber(): String? {
        return dataStore.data.map { prefs ->
            prefs[MEMBER_PHONE_KEY]
        }.first()
    }

    suspend fun getMemberEmail(): String? {
        return dataStore.data.map { prefs ->
            prefs[MEMBER_EMAIL_KEY]
        }.first()
    }

    suspend fun getMatchingCount(): String? {
        return dataStore.data.map { prefs ->
            prefs[MEMBER_MATCHING_COUNT_KEY]
        }.first()
    }

    suspend fun getMemberBlocked(): String? {
        return dataStore.data.map { prefs ->
            prefs[MEMBER_BLOCKED_KEY]
        }.first()
    }

    suspend fun putAccessToken(token: String) {
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN_KEY] = token
        }
    }

    suspend fun putRefreshToken(token: String) {
        dataStore.edit { prefs ->
            prefs[REFRESH_TOKEN_KEY] = token
        }
    }

    suspend fun putMemberId(id: Long) {
        dataStore.edit { prefs ->
            prefs[MEMBER_ID_KEY] = id
        }
    }

    suspend fun putMemberName(name: String) {
        dataStore.edit { prefs ->
            prefs[MEMBER_NAME_KEY] = name
        }
    }

    suspend fun putMemberGender(gender: String) {
        dataStore.edit { prefs ->
            prefs[MEMBER_GENDER_KEY] = gender
        }
    }

    suspend fun putMemberPhoneNumber(phoneNumber: String) {
        dataStore.edit { prefs ->
            prefs[MEMBER_PHONE_KEY] = phoneNumber
        }
    }

    suspend fun putMemberEmail(email: String) {
        dataStore.edit { prefs ->
            prefs[MEMBER_EMAIL_KEY] = email
        }
    }

    suspend fun putMatchingCount(matchingCount: String) {
        dataStore.edit { prefs ->
            prefs[MEMBER_MATCHING_COUNT_KEY] = matchingCount
        }
    }

    suspend fun putMemberBlocked(blocked: String) {
        dataStore.edit { prefs ->
            prefs[MEMBER_BLOCKED_KEY] = blocked
        }
    }

    suspend fun deleteAccessToken() {
        dataStore.edit { prefs ->
            prefs.remove(ACCESS_TOKEN_KEY)
        }
    }

    suspend fun deleteRefreshToken() {
        dataStore.edit { prefs ->
            prefs.remove(REFRESH_TOKEN_KEY)
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

    suspend fun deleteMemberGender() {
        dataStore.edit { prefs ->
            prefs.remove(MEMBER_GENDER_KEY)
        }
    }

    suspend fun deleteMemberPhoneNumber() {
        dataStore.edit { prefs ->
            prefs.remove(MEMBER_PHONE_KEY)
        }
    }

    suspend fun deleteMemberEmail() {
        dataStore.edit { prefs ->
            prefs.remove(MEMBER_EMAIL_KEY)
        }
    }

    suspend fun deleteMatchingCount() {
        dataStore.edit { prefs ->
            prefs.remove(MEMBER_MATCHING_COUNT_KEY)
        }
    }

    suspend fun deleteMemberBlocked() {
        dataStore.edit { prefs ->
            prefs.remove(MEMBER_BLOCKED_KEY)
        }
    }

    suspend fun putProfileUrl(url: String) {
        dataStore.edit { prefs ->
            prefs[PROFILE_URL_KEY] = url
        }
    }

    suspend fun deleteProfileUrl() {
        dataStore.edit { prefs ->
            prefs.remove(PROFILE_URL_KEY)
        }
    }

    suspend fun getProfileUrl(): String? {
        return dataStore.data.map { prefs ->
            prefs[PROFILE_URL_KEY]
        }.first()
    }
}