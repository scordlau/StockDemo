package com.example.core.domain

import com.example.core.data.datamodel.CurrencyModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by scordlau on 5/27/21.
 */

interface ViewCurrencyListUseCase {

    fun getCurrencyList(): Flow<List<CurrencyModel>>

    fun getIsFirstLoad(): Boolean

}