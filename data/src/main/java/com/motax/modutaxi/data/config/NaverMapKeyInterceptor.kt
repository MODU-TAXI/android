package com.motax.modutaxi.data.config

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class NaverMapKeyInterceptor @Inject constructor(
    private val naverMapClientId: String,
    private val naverMapClientSecret: String
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val builder: Request.Builder = chain.request().newBuilder()
        builder.addHeader("X-NCP-APIGW-API-KEY-ID", naverMapClientId)
        builder.addHeader("X-NCP-APIGW-API-KEY", naverMapClientSecret)
        return chain.proceed(builder.build())
    }
}