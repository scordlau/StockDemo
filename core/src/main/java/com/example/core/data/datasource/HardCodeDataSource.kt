package com.example.core.data.datasource

import com.example.core.data.datamodel.CurrencyListModel

/**
 * Created by scordlau on 5/26/21.
 */

class HardCodeDataSource : CurrencyInfoDataSource {
    override suspend fun retrieveAll(): CurrencyListModel? {
        return CurrencyListModel(mapOf(Pair("test", 1.0), Pair("test2", 2.0)))
    }

}