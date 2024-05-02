package com.motax.modutaxi.domain.repository

import com.motax.modutaxi.domain.model.AuthData
import com.motax.modutaxi.domain.model.MemberCheckData

interface IntroRepository {

    suspend fun login(
        type: String,
        accessToken: String
    ): Result<AuthData>

    suspend fun memberCheck(
        type: String,
        accessToken: String
    ): Result<MemberCheckData>

    suspend fun signUp(
        key: String,
        name: String,
        gender: String,
        phoneNumber: String
    ): Result<AuthData>
}