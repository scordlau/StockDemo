package com.example.core.data.datasource

import com.example.core.data.datamodel.CurrencyListModel
import com.example.core.data.datamodel.CurrencyModel
import java.util.*
import kotlin.random.Random

class FakeNetworkRandomDataSource(private val localData: LinkedHashMap<String, CurrencyModel>) : CurrencyInfoDataSource {

    override suspend fun retrieveAll(): CurrencyListModel? {
        return CurrencyListModel(fluctuateData(localData))
    }

    private fun Double.randomChange() = this + Random.nextDouble(-0.0010, 0.0010)

    private fun fluctuateData(localData: LinkedHashMap<String, CurrencyModel>): Map<String, Double> {
        return localData.entries.associate { (name, value) ->
            name to value.rate.randomChange()
        }
    }

}
