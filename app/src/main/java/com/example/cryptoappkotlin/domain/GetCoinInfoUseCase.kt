package com.example.cryptoappkotlin.domain

class GetCoinInfoUseCase(private val repository: CoinRepository) {

    operator fun invoke(fromSym: String) = repository.getCoinInfo(fromSym)
}