package com.motax.modutaxi.data.config

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.motax.modutaxi.data.Constants
import com.motax.modutaxi.data.Constants.ACCESS_TOKEN
import com.motax.modutaxi.data.Constants.MEMBER_BLOCKED
import com.motax.modutaxi.data.Constants.MEMBER_EMAIL
import com.motax.modutaxi.data.Constants.MEMBER_GENDER
import com.motax.modutaxi.data.Constants.MEMBER_MATCHING_COUNT
import com.motax.modutaxi.data.Constants.REFRESH_TOKEN
import com.motax.modutaxi.data.Constants.MEMBER_ID
import com.motax.modutaxi.data.Constants.MEMBER_NAME
import com.motax.modutaxi.data.Constants.MEMBER_PHONE_NUMBER
import com.motax.modutaxi.data.Constants.PROFILE_URL
import com.motax.modutaxi.data.Constants.SNS_ID
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
        private val MEMBER_ID_KEY = stringPreferencesKey(MEMBER_ID)
        private val MEMBER_NAME_KEY = stringPreferencesKey(MEMBER_NAME)
        private val GENDER_KEY = stringPreferencesKey(MEMBER_GENDER)
        private val PHONE_NUMBER_KEY = stringPreferencesKey(MEMBER_PHONE_NUMBER)
        private val EMAIL_KEY = stringPreferencesKey(MEMBER_EMAIL)
        private val MATCHING_COUNT_KEY = stringPreferencesKey(MEMBER_MATCHING_COUNT)
        private val BLOCKED_KEY = stringPreferencesKey(MEMBER_BLOCKED)
        private val PROFILE_URL_KEY = stringPreferencesKey(PROFILE_URL)
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

    suspend fun deleteMemberName() {
        dataStore.edit { prefs ->
            prefs.remove(MEMBER_NAME_KEY)
        }
    }

    suspend fun deleteMemberId() {
        dataStore.edit { prefs ->
            prefs.remove(MEMBER_ID_KEY)
        }
    }

    suspend fun getGender(): String? {
        return dataStore.data.map { prefs ->
            prefs[GENDER_KEY]
        }.first()
    }

    suspend fun getPhoneNumber(): String? {
        return dataStore.data.map { prefs ->
            prefs[PHONE_NUMBER_KEY]
        }.first()
    }

    suspend fun getEmail(): String? {
        return dataStore.data.map { prefs ->
            prefs[EMAIL_KEY]
        }.first()
    }

    suspend fun getMatchingCount(): String? {
        return dataStore.data.map { prefs ->
            prefs[MATCHING_COUNT_KEY]
        }.first()
    }

    suspend fun getBlocked(): String? {
        return dataStore.data.map { prefs ->
            prefs[BLOCKED_KEY]
        }.first()
    }

    suspend fun getProfileUrl(): String? {
        return dataStore.data.map { prefs ->
            prefs[PROFILE_URL_KEY]
        }.first()
    }

    suspend fun putGender(gender: String) {
        dataStore.edit { prefs ->
            prefs[GENDER_KEY] = gender
        }
    }

    suspend fun putPhoneNumber(phoneNumber: String) {
        dataStore.edit { prefs ->
            prefs[PHONE_NUMBER_KEY] = phoneNumber
        }
    }

    suspend fun putEmail(email: String) {
        dataStore.edit { prefs ->
            prefs[EMAIL_KEY] = email
        }
    }

    suspend fun putMatchingCount(matchingCount: String) {
        dataStore.edit { prefs ->
            prefs[MATCHING_COUNT_KEY] = matchingCount
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

    suspend fun putBlocked(blocked: String) {
        dataStore.edit { prefs ->
            prefs[BLOCKED_KEY] = blocked
        }
    }

    suspend fun putProfileUrl(url: String) {
        dataStore.edit { prefs ->
            prefs[PROFILE_URL_KEY] = url
        }
    }

    suspend fun deleteGender() {
        dataStore.edit { prefs ->
            prefs.remove(GENDER_KEY)
        }
    }

    suspend fun deletePhoneNumber() {
        dataStore.edit { prefs ->
            prefs.remove(PHONE_NUMBER_KEY)
        }
    }

    suspend fun deleteEmail() {
        dataStore.edit { prefs ->
            prefs.remove(EMAIL_KEY)
        }
    }

    suspend fun deleteMatchingCount() {
        dataStore.edit { prefs ->
            prefs.remove(MATCHING_COUNT_KEY)
        }
    }

    suspend fun deleteBlocked() {
        dataStore.edit { prefs ->
            prefs.remove(BLOCKED_KEY)
        }
    }

    suspend fun deleteProfileUrl() {
        dataStore.edit { prefs ->
            prefs.remove(PROFILE_URL_KEY)
        }
    }
}