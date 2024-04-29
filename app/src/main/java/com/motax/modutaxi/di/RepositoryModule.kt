package com.motax.modutaxi.di

import com.motax.modutaxi.data.remote.IntroApi
import com.motax.modutaxi.data.repository.IntroRepository
import com.motax.modutaxi.data.repository.IntroRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

//    @Singleton
//    @Binds
//    abstract fun bindMyPageRepository(myPageRepositoryImpl: MyPageRepositoryImpl): MyPageRepository

    @Singleton
    @Provides
    fun provideIntroRepository(api: IntroApi): IntroRepository {
        return IntroRepositoryImpl(api)
    }
}