package com.example.cryptoappkotlin.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoappkotlin.R
import com.example.cryptoappkotlin.databinding.ActivityCoinPriceListBinding
import com.example.cryptoappkotlin.domain.CoinInfo
import com.example.cryptoappkotlin.presentation.CoinDetailActivity.Companion.newIntent
import com.example.cryptoappkotlin.presentation.CoinDetailFragment.Companion.newInstance
import com.example.cryptoappkotlin.presentation.adapters.CoinInfoAdapter
import javax.inject.Inject

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinPriceListBinding

    private lateinit var viewModel: CoinViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as CoinApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityCoinPriceListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[CoinViewModel::class.java]
        getCoinInfo(this)
    }

    private fun getCoinInfo(context: Context) {
        val adapter = CoinInfoAdapter(context)
        binding.rvCoinPriceList.adapter = adapter

        viewModel.coinInfoList.observe(this) {
            adapter.submitList(it)
        }

        adapter.onCoinDetailClickListener = object : CoinInfoAdapter.OnCoinDetailClickListener {
            override fun onClick(coinPriceInfo: CoinInfo) {
                if (isOnePaneMode()) {
                    launchDetailActivity(coinPriceInfo.fromSymbol)
                } else {
                    launchDetailFragment(coinPriceInfo.fromSymbol)
                }
            }
        }
    }

    private fun isOnePaneMode() = binding.fragmentContainer == null

    private fun launchDetailActivity(fromSymbol: String) {
        val intent = newIntent(this@CoinPriceListActivity, fromSymbol)
        startActivity(intent)
    }

    private fun launchDetailFragment(fromSymbol: String) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, newInstance(fromSymbol))
            .addToBackStack(null)
            .commit()
    }
}