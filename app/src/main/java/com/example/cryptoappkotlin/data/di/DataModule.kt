package com.example.cryptoappkotlin.data.di

import android.app.Application
import com.example.cryptoappkotlin.data.database.AppDatabase
import com.example.cryptoappkotlin.data.database.CoinInfoDao
import com.example.cryptoappkotlin.data.network.ApiFactory
import com.example.cryptoappkotlin.data.network.ApiService
import com.example.cryptoappkotlin.data.repository.CoinRepositoryImpl
import com.example.cryptoappkotlin.domain.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository

    companion object {

        @Provides
        @ApplicationScope
        fun provideCoinInfoDao(
            application: Application
        ): CoinInfoDao {
            return AppDatabase.getInstance(application).coinInfoDao()
        }

        @Provides
        @ApplicationScope
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}