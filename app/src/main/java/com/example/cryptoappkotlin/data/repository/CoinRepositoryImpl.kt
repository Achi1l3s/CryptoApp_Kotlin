package com.example.cryptoappkotlin.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.cryptoappkotlin.data.database.AppDatabase
import com.example.cryptoappkotlin.data.database.CoinInfoDao
import com.example.cryptoappkotlin.data.mapper.CoinMapper
import com.example.cryptoappkotlin.data.network.ApiFactory
import com.example.cryptoappkotlin.data.workers.RefreshDataWorker
import com.example.cryptoappkotlin.data.workers.RefreshDataWorker.Companion.WORKER_NAME
import com.example.cryptoappkotlin.data.workers.RefreshDataWorker.Companion.makeRequest
import com.example.cryptoappkotlin.domain.CoinInfo
import com.example.cryptoappkotlin.domain.CoinRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val application: Application,
    private val mapper: CoinMapper,
    private val coinInfoDao: CoinInfoDao
) : CoinRepository {

    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return coinInfoDao.getPriceList().map { it ->
            it.map { mapper.mapDbModelToEntity(it) }
        }
    }

    override fun getCoinInfo(fromSym: String): LiveData<CoinInfo> {
        return coinInfoDao.getPriceInfoAboutCoin(fromSym).map {
            mapper.mapDbModelToEntity(it)
        }
    }

    override fun loadData() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            WORKER_NAME,
            ExistingWorkPolicy.REPLACE,
            makeRequest()
        )
    }
}