package com.motax.modutaxi.domain.repository

import com.motax.modutaxi.domain.model.AuthData
import com.motax.modutaxi.domain.model.MemberCheckData
import com.motax.modutaxi.domain.model.CertificateData

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