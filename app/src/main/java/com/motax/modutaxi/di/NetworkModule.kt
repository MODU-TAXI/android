package com.motax.modutaxi.di

import com.motax.modutaxi.BuildConfig
import com.motax.modutaxi.data.config.AccessTokenInterceptor
import com.motax.modutaxi.data.config.DataStoreManager
import com.motax.modutaxi.data.config.NaverKeyInterceptor
import com.motax.modutaxi.data.remote.IntroApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NaverRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NaverOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NaverClientId

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NaverClientSecret

    @Provides
    @Singleton
    @BaseOkHttpClient
    fun provideBaseOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        accessTokenInterceptor: AccessTokenInterceptor,
    ): OkHttpClient {

        return OkHttpClient.Builder()
            .readTimeout(10000, TimeUnit.MILLISECONDS)
            .connectTimeout(10000, TimeUnit.MILLISECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor(accessTokenInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @NaverOkHttpClient
    fun provideNaverOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        naverKeyInterceptor: NaverKeyInterceptor
    ): OkHttpClient {

        return OkHttpClient.Builder()
            .readTimeout(10000, TimeUnit.MILLISECONDS)
            .connectTimeout(10000, TimeUnit.MILLISECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor(naverKeyInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    @NaverClientId
    fun provideNaverClientId(): String = BuildConfig.NAVER_CLIENT_ID

    @Provides
    @Singleton
    @NaverClientSecret
    fun provideNaverClientSecret(): String = BuildConfig.NAVER_CLIENT_SECRET

    @Provides
    @Singleton
    fun provideAccessTokenInterceptor(dataStoreManager: DataStoreManager): AccessTokenInterceptor =
        AccessTokenInterceptor(dataStoreManager)

    @Provides
    @Singleton
    fun provideNaverKeyInterceptor(
        @NaverClientId naverClientId : String,
        @NaverClientSecret naverClientSecret : String
    ): NaverKeyInterceptor =
        NaverKeyInterceptor(naverClientId, naverClientSecret)

    @Provides
    @Singleton
    @BaseRetrofit
    fun provideBaseRetrofit(@BaseOkHttpClient okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_TEST_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @NaverRetrofit
    fun provideNaverRetrofit(@NaverOkHttpClient okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BuildConfig.NAVER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}