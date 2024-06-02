package com.motax.modutaxi.data.remote

import com.motax.modutaxi.data.model.request.EmailCertificationRequest
import com.motax.modutaxi.data.model.request.EmailConfirmRequest
import com.motax.modutaxi.data.model.request.LoginRequest
import com.motax.modutaxi.data.model.request.SignUpRequest
import com.motax.modutaxi.data.model.request.SmsCertificateRequest
import com.motax.modutaxi.data.model.request.SmsConfirmRequest
import com.motax.modutaxi.data.model.response.TokenResponse
import com.motax.modutaxi.data.model.response.MemberCheckResponse
import com.motax.modutaxi.data.model.response.CertificateResponse
import com.motax.modutaxi.data.model.response.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface IntroApi {

    @POST("api/members/{type}/login")
    suspend fun login(
        @Path("type") type: String,
        @Body body: LoginRequest
    ): AuthResponse

    @POST("/api/members/{type}/membership")
    suspend fun memberCheck(
        @Path("type") type: String,
        @Body body: LoginRequest
    ): MemberCheckResponse

    @POST("/api/members/sign-up")
    suspend fun signUp(
        @Body body: SignUpRequest
    ): AuthResponse

    @POST("/api/members/sms/confirm")
    suspend fun smsConfirm(
        @Body body: SmsConfirmRequest
    ): CertificateResponse

    @POST("/api/members/sms/certificate")
    suspend fun smsCertificate(
        @Body body: SmsCertificateRequest
    ): CertificateResponse

    @POST("/api/members/mail/confirm")
    suspend fun emailConfirm(
        @Body body: EmailConfirmRequest
    ): CertificateResponse

    @POST("/api/members/mail/certificate")
    suspend fun emailCertificate(
        @Body body: EmailCertificationRequest
    ): CertificateResponse
}