package com.example.cryptoappkotlin.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoappkotlin.data.repository.CoinRepositoryImpl
import com.example.cryptoappkotlin.domain.GetCoinInfoListUseCase
import com.example.cryptoappkotlin.domain.GetCoinInfoUseCase
import com.example.cryptoappkotlin.domain.LoadDataUseCase
import kotlinx.coroutines.launch

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CoinRepositoryImpl(application)

    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repository)
    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    fun getDetailInfo(fSim: String) = getCoinInfoUseCase(fSim)

    val coinInfoList = getCoinInfoListUseCase()

    init {
        viewModelScope.launch {
            loadDataUseCase()
        }
    }
}