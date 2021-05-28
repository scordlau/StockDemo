package com.example.currencydemo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.currencydemo.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class DemoActivity : AppCompatActivity() {

    var currencyListFragment: CurrencyListFragment? = CurrencyListFragment.newInstance(Bundle())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        val navView = findViewById<BottomNavigationView>(R.id.bottom_navigatin_view)
        currencyListFragment?.let { fragment ->
            replaceFragment(fragment)
        }

        navView.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.nav_market -> currencyListFragment?.let { fragment ->
                    replaceFragment(fragment)
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(
                        R.id.linear_layout_demo,
                        fragment,
                        this@DemoActivity.javaClass.name)
                .commit()
    }
}