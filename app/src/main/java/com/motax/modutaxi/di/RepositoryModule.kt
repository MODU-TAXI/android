package com.motax.modutaxi.di

import com.motax.modutaxi.data.repository.IntroRepositoryImpl
import com.motax.modutaxi.data.repository.MainRepositoryImpl
import com.motax.modutaxi.domain.repository.IntroRepository
import com.motax.modutaxi.domain.repository.MainRepository
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
}