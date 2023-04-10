package com.example.poilabschallange.di

import com.example.poilabschallange.data.remote.api.HomeService
import com.example.poilabschallange.data.repository.HomeRepository
import com.example.poilabschallange.data.repository.impl.HomeRepositoryImpl
import com.example.poilabschallange.domain.usecase.HomeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HomeDI {
    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): HomeService = retrofit.create(HomeService::class.java)

    @Provides
    @Singleton
    fun provideRepository(service: HomeService): HomeRepository = HomeRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideUseCase(
        homeRepository: HomeRepository
    ): HomeUseCase =
        HomeUseCase(
            homeRepository
        )
}