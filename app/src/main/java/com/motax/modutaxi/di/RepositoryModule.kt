package com.motax.modutaxi.di

import com.motax.modutaxi.data.repository.IntroRepositoryImpl
import com.motax.modutaxi.data.repository.MainRepositoryImpl
import com.motax.modutaxi.data.repository.NaverMapRepositoryImpl
import com.motax.modutaxi.data.repository.NaverRepositoryImpl
import com.motax.modutaxi.domain.repository.IntroRepository
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.domain.repository.NaverMapRepository
import com.motax.modutaxi.domain.repository.NaverRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindIntroRepository(introRepositoryImpl: IntroRepositoryImpl): IntroRepository

    @Singleton
    @Binds
    abstract fun bindMainRepository(mainRepositoryImpl: MainRepositoryImpl): MainRepository

    @Singleton
    @Binds
    abstract fun bindNaverRepository(naverRepositoryImpl: NaverRepositoryImpl): NaverRepository

    @Singleton
    @Binds
    abstract fun bindNaverMapRepository(naverMapRepositoryImpl: NaverMapRepositoryImpl): NaverMapRepository
}