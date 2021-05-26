package com.example.core.data

import kotlin.random.Random

/**
 * Created by scordlau on 5/25/21.
 */

data class CurrencyModel(val name: String,
                         val rate: Double,
                         val originRate: Double,
) {

    val sellRate: Double
    val buyRate: Double
    val percentageChange: Double
    val isGreen: Boolean

    init {
        buyRate = rate + randomDiff()
        sellRate = rate - randomDiff()
        percentageChange = (sellRate - originRate) / originRate * 100
        isGreen = percentageChange >= 0
    }

    private fun randomDiff() = Random.nextDouble(0.0001, 0.0010)

    fun updateRate(rate: Double): CurrencyModel {
        return this.copy(rate = rate)
    }

}

