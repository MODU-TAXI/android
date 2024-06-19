package com.motax.modutaxi.di

import com.motax.modutaxi.data.remote.AuthApi
import com.motax.modutaxi.data.remote.ImageApi
import com.motax.modutaxi.data.remote.IntroApi
import com.motax.modutaxi.data.remote.MainApi
import com.motax.modutaxi.data.remote.NaverApi
import com.motax.modutaxi.data.remote.NaverMapApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideAuthApi(@NetworkModule.BaseRetrofit retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Singleton
    @Provides
    fun provideIntroApi(@NetworkModule.BaseRetrofit retrofit: Retrofit): IntroApi =
        retrofit.create(IntroApi::class.java)

    @Singleton
    @Provides
    fun provideMainApi(@NetworkModule.BaseRetrofit retrofit: Retrofit): MainApi =
        retrofit.create(MainApi::class.java)

    @Singleton
    @Provides
    fun provideImageApi(@NetworkModule.BaseRetrofit retrofit: Retrofit): ImageApi =
        retrofit.create(ImageApi::class.java)

    @Singleton
    @Provides
    fun provideNaverApi(@NetworkModule.NaverRetrofit retrofit: Retrofit): NaverApi =
        retrofit.create(NaverApi::class.java)

    @Singleton
    @Provides
    fun provideNaverMapApi(@NetworkModule.NaverMapRetrofit retrofit: Retrofit): NaverMapApi =
        retrofit.create(NaverMapApi::class.java)
}