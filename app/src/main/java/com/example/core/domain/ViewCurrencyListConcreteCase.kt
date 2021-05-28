package com.example.core.domain

import com.example.core.data.datamodel.CurrencyModel
import com.example.core.data.repository.CurrencyInfoRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by scordlau on 5/27/21.
 */

class ViewCurrencyListConcreteCase(private val repository: CurrencyInfoRepository) : ViewCurrencyListUseCase {

    override fun getCurrencyList(): Flow<List<CurrencyModel>> {
        return repository.outputFlow
    }

    override fun getIsFirstLoad(): Boolean {
        return repository.isFirstsLoad.get()
    }

}