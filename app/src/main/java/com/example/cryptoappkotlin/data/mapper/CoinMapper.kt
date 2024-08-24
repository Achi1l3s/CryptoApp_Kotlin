package com.example.cryptoappkotlin.data.mapper

import com.example.cryptoappkotlin.data.database.CoinInfoDbModel
import com.example.cryptoappkotlin.data.network.model.CoinInfoDto
import com.example.cryptoappkotlin.data.network.model.CoinInfoJsonContainerDto
import com.example.cryptoappkotlin.data.network.model.CoinNamesListDto
import com.example.cryptoappkotlin.domain.CoinInfo
import com.google.gson.Gson
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class CoinMapper {

    fun mapDtoToDbModel(dto: CoinInfoDto) = CoinInfoDbModel(
        fromSymbol = dto.fromSymbol,
        toSymbol = dto.toSymbol,
        lastMarket = dto.lastMarket,
        price = dto.price,
        lastUpdate = dto.lastUpdate,
        highDay = dto.highDay,
        lowDay = dto.lowDay,
        imageUrl = BASE_IMAGE_URL + dto.imageUrl
    )

    fun mapJsonContainerToListCoinInfo(jsonContainer: CoinInfoJsonContainerDto): List<CoinInfoDto> {
        val result = mutableListOf<CoinInfoDto>()
        val jsonObj = jsonContainer.json ?: return result
        val coinKeySet = jsonObj.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObj.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    fun mapCoinNamesListToString(coinNamesListDto: CoinNamesListDto): String {
        return coinNamesListDto.names?.map {
            it.coinName?.name
        }?.joinToString(",") ?: ""
    }

    fun mapDbModelToEntity(dbModel: CoinInfoDbModel): CoinInfo {
        return CoinInfo(
            fromSymbol = dbModel.fromSymbol,
            toSymbol = dbModel.toSymbol,
            lastMarket = dbModel.lastMarket,
            price = priceFormat(dbModel.price),
            lastUpdate = convertTimestampToTime(dbModel.lastUpdate),
            highDay = priceFormat(dbModel.highDay),
            lowDay = priceFormat(dbModel.lowDay),
            imageUrl = dbModel.imageUrl
        )
    }

    private fun convertTimestampToTime(timestamp: Long?): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss, dd/MM/yyyy"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    private fun priceFormat(fromSymbol: Double?): String {
        return if (fromSymbol != null && fromSymbol > 1.0) {
            String.format(Locale.getDefault(), "%.2f", fromSymbol)
        } else {
            String.format(Locale.getDefault(),"%.8f", fromSymbol)
        }
    }

    companion object {
        const val BASE_IMAGE_URL = "https://www.cryptocompare.com"
    }
}