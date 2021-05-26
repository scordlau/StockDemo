package com.example.currencydemo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.currencydemo.R

class DemoActivity : AppCompatActivity() {

    var currencyListFragment: CurrencyListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        createCurrencyListFragment()
    }

    private fun createCurrencyListFragment() {
        currencyListFragment = CurrencyListFragment.newInstance(Bundle()).also {
            supportFragmentManager.beginTransaction().replace(
                    R.id.linear_layout_demo,
                    it,
                    this.javaClass.name
            ).commit()
        }
    }
}