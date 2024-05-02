package com.motax.modutaxi.data.repository

import com.motax.modutaxi.data.model.mapper.toDomain
import com.motax.modutaxi.data.model.request.EmailCertificationRequest
import com.motax.modutaxi.data.model.request.EmailConfirmRequest
import com.motax.modutaxi.data.model.request.LoginRequest
import com.motax.modutaxi.data.model.request.SignUpRequest
import com.motax.modutaxi.data.model.request.SmsCertificateRequest
import com.motax.modutaxi.data.model.request.SmsConfirmRequest
import com.motax.modutaxi.data.remote.IntroApi
import com.motax.modutaxi.domain.model.AuthData
import com.motax.modutaxi.domain.model.CertificateData
import com.motax.modutaxi.domain.model.MemberCheckData
import com.motax.modutaxi.domain.repository.IntroRepository
import javax.inject.Inject

class IntroRepositoryImpl @Inject constructor(
    private val api: IntroApi
) : IntroRepository {

    override suspend fun login(type: String, accessToken: String): Result<AuthData> =
        runCatching { api.login(type, LoginRequest(accessToken)) }.mapCatching { it.toDomain() }

    override suspend fun memberCheck(type: String, accessToken: String): Result<MemberCheckData> =
        runCatching {
            api.memberCheck(
                type,
                LoginRequest(accessToken)
            )
        }.mapCatching { it.toDomain() }

    override suspend fun signUp(
        key: String,
        name: String,
        gender: String,
        phoneNumber: String
    ): Result<AuthData> =
        runCatching {
            api.signUp(
                SignUpRequest(
                    key,
                    name,
                    gender,
                    phoneNumber
                )
            )
        }.mapCatching { it.toDomain() }

    override suspend fun smsCertificate(key: String, phoneNumber: String): Result<CertificateData> =
        runCatching {
            api.smsCertificate(SmsCertificateRequest(key, phoneNumber))
        }.mapCatching { it.toDomain() }

    override suspend fun smsConfirm(
        key: String,
        phoneNumber: String,
        certificationCode: String
    ): Result<CertificateData> =
        runCatching { api.smsConfirm(SmsConfirmRequest(key, phoneNumber, certificationCode)) }
            .mapCatching { it.toDomain() }

    override suspend fun emailCertificate(mailAddress: String): Result<CertificateData> =
        runCatching { api.emailCertificate(EmailCertificationRequest(mailAddress)) }.mapCatching { it.toDomain() }

    override suspend fun emailConfirm(certCode: String): Result<CertificateData> =
        runCatching { api.emailConfirm(EmailConfirmRequest(certCode)) }.mapCatching { it.toDomain() }


}