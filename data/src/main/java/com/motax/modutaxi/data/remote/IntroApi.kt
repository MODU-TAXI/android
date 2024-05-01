package com.motax.modutaxi.data.remote

import com.motax.modutaxi.data.model.request.LoginRequest
import com.motax.modutaxi.data.model.request.SignUpRequest
import com.motax.modutaxi.data.model.response.AuthResponse
import com.motax.modutaxi.data.model.response.MemberCheckResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface IntroApi {

    @POST("api/members/{type}/login")
    suspend fun login(
        @Path("type") type: String,
        @Body body: LoginRequest
    ): Result<AuthResponse>

    @POST("/api/members/{type}/membership")
    suspend fun memberCheck(
        @Path("type") type: String,
        @Body body: LoginRequest
    ): Result<MemberCheckResponse>

    @POST("/api/members/sign-up")
    suspend fun signUp(
        @Body body: SignUpRequest
    ): Result<AuthResponse>
}