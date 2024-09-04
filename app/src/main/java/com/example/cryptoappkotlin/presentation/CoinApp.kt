package com.example.cryptoappkotlin.presentation

import android.app.Application
import androidx.work.Configuration
import com.example.cryptoappkotlin.data.di.DaggerApplicationComponent
import com.example.cryptoappkotlin.data.workers.CoinWorkerFactory
import javax.inject.Inject

class CoinApp : Application(), Configuration.Provider {

    @Inject
    lateinit var coinWorkerFactory: CoinWorkerFactory

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(coinWorkerFactory)
            .build()
}