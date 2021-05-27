package com.example.core.data.datasource

import com.example.core.data.datamodel.CurrencyListModel

interface CurrencyInfoDataSource {

    suspend fun retrieveAll(): CurrencyListModel?

}
