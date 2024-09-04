package com.example.cryptoappkotlin.presentation

import androidx.lifecycle.ViewModel
import com.example.cryptoappkotlin.domain.GetCoinInfoListUseCase
import com.example.cryptoappkotlin.domain.GetCoinInfoUseCase
import com.example.cryptoappkotlin.domain.LoadDataUseCase
import javax.inject.Inject

class CoinViewModel @Inject constructor(
    private val getCoinInfoListUseCase: GetCoinInfoListUseCase,
    private val getCoinInfoUseCase: GetCoinInfoUseCase,
    private val loadDataUseCase: LoadDataUseCase
) : ViewModel() {

    fun getDetailInfo(fSim: String) = getCoinInfoUseCase(fSim)

    val coinInfoList = getCoinInfoListUseCase()

    init {
        loadDataUseCase()
    }
}