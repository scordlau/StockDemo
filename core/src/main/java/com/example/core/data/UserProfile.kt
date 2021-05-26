package com.example.core.data

import java.util.concurrent.atomic.AtomicInteger

object UserProfile {

    fun updateProfileBalance(size: Int): Int {
        balance.set(size * DEFAULT_LONG_POS)
        return balance.get()
    }

    fun updateProfileEquity(quotes: List<CurrencyModel>?): Int {
        var sum = 0.0
        quotes?.forEach {
            sum += it.sellRate
        }
        equity.set((DEFAULT_LONG_POS * sum).toInt())
        return equity.get()
    }

    val balance = AtomicInteger(0)
    val equity = AtomicInteger(0)
    private const val DEFAULT_LONG_POS = 10000

}
