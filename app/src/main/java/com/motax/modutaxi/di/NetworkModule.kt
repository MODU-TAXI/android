package com.motax.modutaxi.di

import com.motax.modutaxi.BuildConfig
import com.motax.modutaxi.data.config.AccessTokenInterceptor
import com.motax.modutaxi.data.config.BearerInterceptor
import com.motax.modutaxi.data.config.DataStoreManager
import com.motax.modutaxi.data.config.NaverKeyInterceptor
import com.motax.modutaxi.data.config.NaverMapKeyInterceptor
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
    annotation class NaverMapRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NaverOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NaverMapOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseUrl

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NaverClientId

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NaverClientSecret

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NaverMapClientId

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NaverMapClientSecret

    @Provides
    @Singleton
    @BaseOkHttpClient
    fun provideBaseOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        accessTokenInterceptor: AccessTokenInterceptor,
        bearerInterceptor: BearerInterceptor
    ): OkHttpClient {

        return OkHttpClient.Builder()
            .readTimeout(10000, TimeUnit.MILLISECONDS)
            .connectTimeout(10000, TimeUnit.MILLISECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(bearerInterceptor)
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
    @NaverMapOkHttpClient
    fun provideNaverMapOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        naverMapKeyInterceptor: NaverMapKeyInterceptor
    ): OkHttpClient {

        return OkHttpClient.Builder()
            .readTimeout(10000, TimeUnit.MILLISECONDS)
            .connectTimeout(10000, TimeUnit.MILLISECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor(naverMapKeyInterceptor)
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
    @BaseUrl
    fun provideBaseUrl(): String = BuildConfig.BASE_TEST_URL

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
    @NaverMapClientId
    fun provideNaverMapClientId(): String = BuildConfig.NAVER_MAP_CLIENT_ID

    @Provides
    @Singleton
    @NaverMapClientSecret
    fun provideNaverMapClientSecret(): String = BuildConfig.NAVER_MAP_CLIENT_SECRET

    @Provides
    @Singleton
    fun provideBearerInterceptor(
        @BaseUrl baseUrl: String,
        dataStoreManager: DataStoreManager
    ): BearerInterceptor = BearerInterceptor(dataStoreManager, baseUrl)

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
    fun provideNaverMapKeyInterceptor(
        @NaverMapClientId naverMapClientId : String,
        @NaverMapClientSecret naverMapClientSecret : String
    ): NaverMapKeyInterceptor =
        NaverMapKeyInterceptor(naverMapClientId, naverMapClientSecret)

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
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @NaverMapRetrofit
    fun provideNaverMapRetrofit(@NaverMapOkHttpClient okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BuildConfig.NAVER_MAP_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}