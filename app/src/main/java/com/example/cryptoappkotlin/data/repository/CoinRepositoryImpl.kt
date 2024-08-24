package com.example.cryptoappkotlin.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.cryptoappkotlin.data.database.AppDatabase
import com.example.cryptoappkotlin.data.mapper.CoinMapper
import com.example.cryptoappkotlin.data.network.ApiFactory
import com.example.cryptoappkotlin.domain.CoinInfo
import com.example.cryptoappkotlin.domain.CoinRepository
import kotlinx.coroutines.delay

class CoinRepositoryImpl(private val application: Application) : CoinRepository {

    private val coinInfoDao = AppDatabase.getInstance(application).coinInfoDao()
    private val mapper = CoinMapper()
    private val apiService = ApiFactory.apiService

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

    override suspend fun loadData() {
        while (true) {
            try {
                val topCoins = apiService.getTopCoinsInfo(limit = 50)
                val fromSymbols = mapper.mapCoinNamesListToString(topCoins)
                val jsonContainer = apiService.getFullPriceList(fSyms = fromSymbols)
                val coinInfoDtoList = mapper.mapJsonContainerToListCoinInfo(jsonContainer)
                val dbModelList = coinInfoDtoList.map {
                    mapper.mapDtoToDbModel(it)
                }
                coinInfoDao.insertPriceList(dbModelList)
            } catch (e: Exception) {
            }
            delay(10000)
        }
    }
}