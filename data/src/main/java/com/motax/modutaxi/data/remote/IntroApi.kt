package com.motax.modutaxi.data.remote

import com.motax.modutaxi.data.model.request.LoginRequest
import com.motax.modutaxi.data.model.response.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface IntroApi {

    @POST("api/members/{type}/login")
    suspend fun memberLogin(
        @Path("type") type: String,
        @Body body: LoginRequest
    ): Result<AuthResponse>
}