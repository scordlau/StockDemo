package com.example.currencydemo.data.datasource

import com.example.core.data.CurrencyListModel

interface CurrencyInfoDataSource {

    suspend fun retrieveAll(): CurrencyListModel?

}
