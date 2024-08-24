package com.example.cryptoappkotlin.domain

import androidx.lifecycle.LiveData

interface CoinRepository {

    fun getCoinInfoList(): LiveData<List<CoinInfo>>

    fun getCoinInfo(fromSym: String): LiveData<CoinInfo>

    suspend fun loadData()
}