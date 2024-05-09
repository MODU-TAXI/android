package com.motax.modutaxi.data.config

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class NaverKeyInterceptor @Inject constructor(
    private val naverClientId: String,
    private val naverClientSecret: String
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val builder: Request.Builder = chain.request().newBuilder()
        builder.addHeader("X-Naver-Client-Id", naverClientId)
        builder.addHeader("X-Naver-Client-Secret", naverClientSecret)
        return chain.proceed(builder.build())
    }
}