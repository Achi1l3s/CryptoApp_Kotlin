package com.example.cryptoappkotlin.domain

import javax.inject.Inject

class GetCoinInfoUseCase @Inject constructor(private val repository: CoinRepository) {

    operator fun invoke(fromSym: String) = repository.getCoinInfo(fromSym)
}