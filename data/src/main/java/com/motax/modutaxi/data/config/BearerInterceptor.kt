package com.motax.modutaxi.data.config

import com.motax.modutaxi.data.Constants.AUTHORIZATION
import com.motax.modutaxi.data.model.response.AuthResponse
import com.motax.modutaxi.data.remote.AuthApi
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Inject

class BearerInterceptor @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val baseUrl: String
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val response = chain.proceed(originalRequest)

        var newAccessToken: String? = null

        // API 통신중 특정코드 에러 발생 (accessToken 만료)
        if (response.code == 410) {

            runBlocking {

                val refreshToken = dataStoreManager.getRefreshToken()
                refreshToken?.let { token ->
                    getNewAccessToken(token).onSuccess {
                        dataStoreManager.putAccessToken(it.tokenResponse.accessToken)
                        dataStoreManager.putRefreshToken(it.tokenResponse.refreshToken)
                        newAccessToken = it.tokenResponse.accessToken

                        dataStoreManager.putMemberId(it.memberInfoResponse.id.toString())
                        dataStoreManager.putMemberName(it.memberInfoResponse.name)
                        dataStoreManager.putGender(it.memberInfoResponse.gender)
                        dataStoreManager.putPhoneNumber(it.memberInfoResponse.phoneNumber)
                        dataStoreManager.putEmail(it.memberInfoResponse.email.toString())
                        dataStoreManager.putMatchingCount(it.memberInfoResponse.matchingCount.toString())
                        dataStoreManager.putBlocked(it.memberInfoResponse.blocked.toString())
                    }.onFailure {
                        dataStoreManager.deleteAccessToken()
                        dataStoreManager.deleteRefreshToken()
                    }
                }
            }

            newAccessToken?.let {
                val newRequest = originalRequest.newBuilder()
                    .addHeader(AUTHORIZATION, it)
                    .build()
                return chain.proceed(newRequest)
            }
        }

        return response
    }

    private suspend fun getNewAccessToken(refreshToken: String): Result<AuthResponse> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val api = retrofit.create(AuthApi::class.java)
        return runCatching { api.refreshToken(refreshToken) }
    }
}