package com.motax.modutaxi.domain.repository

import com.motax.modutaxi.domain.model.AuthData
import com.motax.modutaxi.domain.model.BaseState
import com.motax.modutaxi.domain.model.CertificateData
import com.motax.modutaxi.domain.model.MemberCheckData

interface IntroRepository {

    suspend fun login(
        type: String,
        accessToken: String,
        fcmToken: String
    ): Result<AuthData>

    suspend fun memberCheck(
        type: String,
        accessToken: String,
        fcmToken: String
    ): Result<MemberCheckData>

    suspend fun signUp(
        key: String,
        name: String,
        gender: String,
        phoneNumber: String,
        fcmToken: String
    ): Result<AuthData>

    suspend fun editNick(
        nick: String
    ): BaseState<Unit>

    suspend fun smsConfirm(
        key: String,
        phoneNumber: String,
        certificationCode: String
    ): Result<CertificateData>

    suspend fun smsCertificate(
        key: String,
        phoneNumber: String
    ): Result<CertificateData>

    suspend fun emailConfirm(
        certCode: String
    ): Result<CertificateData>

    suspend fun emailCertificate(
        mailAddress: String
    ): Result<CertificateData>
}