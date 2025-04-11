package com.example.cryptoappkotlin.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.cryptoappkotlin.R
import com.example.cryptoappkotlin.databinding.ItemCoinInfoBinding
import com.example.cryptoappkotlin.domain.CoinInfo
import com.squareup.picasso.Picasso

class CoinInfoAdapter(
    private val context: Context
) : ListAdapter<CoinInfo, CoinInfoViewHolder>(CoinInfoDiffCallback) {

    var onCoinDetailClickListener: OnCoinDetailClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding = ItemCoinInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CoinInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = getItem(position)
        with(holder.binding) {
            with(coin) {
                tvSymbols.text = context.getString(R.string.symbols_template, fromSymbol, toSymbol)
                tvPrice.text = price
                tvLastUpdate.text = context.getString(R.string.last_update, lastUpdate)
                Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.image_error)
                    .into(ivDetailLogo)

                root.setOnClickListener {
                    onCoinDetailClickListener?.onClick(this)
                }
            }
        }

    }

    interface OnCoinDetailClickListener {
        fun onClick(coinPriceInfo: CoinInfo)
    }
}