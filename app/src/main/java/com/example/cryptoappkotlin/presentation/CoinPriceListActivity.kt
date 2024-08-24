package com.example.cryptoappkotlin.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoappkotlin.databinding.ActivityCoinPriceListBinding
import com.example.cryptoappkotlin.domain.CoinInfo
import com.example.cryptoappkotlin.presentation.CoinDetailActivity.Companion.newIntent
import com.example.cryptoappkotlin.presentation.adapters.CoinInfoAdapter

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinPriceListBinding

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCoinPriceListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        getCoinInfo(this)
    }

    private fun getCoinInfo(context: Context) {
        val adapter = CoinInfoAdapter(context)
        binding.rvCoinPriceList.adapter = adapter

        viewModel.coinInfoList.observe(this) {
            adapter.coinInfoList = it
        }

        adapter.onCoinDetailClickListener = object : CoinInfoAdapter.OnCoinDetailClickListener {
            override fun onClick(coinPriceInfo: CoinInfo) {
                val intent = newIntent(this@CoinPriceListActivity, coinPriceInfo.fromSymbol)
                startActivity(intent)
                Log.d(TAG, "onCoinClick: ${coinPriceInfo.fromSymbol}")
            }
        }
    }

    companion object {
        private const val TAG = "SSJ2"
    }
}