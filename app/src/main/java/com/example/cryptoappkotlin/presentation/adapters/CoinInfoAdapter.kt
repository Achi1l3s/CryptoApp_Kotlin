package com.example.cryptoappkotlin.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoappkotlin.R
import com.example.cryptoappkotlin.domain.CoinInfo
import com.squareup.picasso.Picasso

class CoinInfoAdapter(val context: Context) :
    RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var coinInfoList: List<CoinInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onCoinDetailClickListener: OnCoinDetailClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_coin_info,
            parent,
            false
        )
        return CoinInfoViewHolder(view)
    }

    override fun getItemCount() = coinInfoList.size

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        with(holder) {
            with(coin) {
                tvSymbols.text = context.getString(R.string.symbols_template, fromSymbol, toSymbol)
                tvPrice.text = price
//                if (price != null && price > 1.0) {
//                    String.format(Locale.getDefault(),"%.2f", price)
//                } else {
//                    String.format(Locale.getDefault(),"%.8f", price)
//                }
                tvLastUpdate.text = context.getString(R.string.last_update, lastUpdate)
                Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.image_error)
                    .into(ivLogoCoin)

                itemView.setOnClickListener {
                    onCoinDetailClickListener?.onClick(this)
                }
            }
        }

    }

    interface OnCoinDetailClickListener {
        fun onClick(coinPriceInfo: CoinInfo)
    }


    inner class CoinInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivLogoCoin = itemView.findViewById<ImageView>(R.id.ivDetailLogo)
        val tvSymbols = itemView.findViewById<TextView>(R.id.tvSymbols)
        val tvPrice = itemView.findViewById<TextView>(R.id.tvPrice)
        val tvLastUpdate = itemView.findViewById<TextView>(R.id.tvLastUpdate)
    }
}