package com.example.cryptoappkotlin.domain

import javax.inject.Inject

class GetCoinInfoListUseCase @Inject constructor(private val repository: CoinRepository) {

    operator fun invoke() = repository.getCoinInfoList()

}