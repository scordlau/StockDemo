package com.example.network.data.datasource

import com.example.core.data.datamodel.CurrencyListModel
import com.example.core.data.datasource.CurrencyInfoDataSource
import com.example.network.ForexService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * Created by scordlau on 5/24/21.
 */

class NetworkCurrencyInfoDataSource(private val isApiError: Channel<Boolean>,
                                    private val forexService: ForexService,
                                    private val dispatcher: CoroutineDispatcher) : CurrencyInfoDataSource {

    override suspend fun retrieveAll(): CurrencyListModel? {
        var result: Response<CurrencyListModel>? = null
        withContext(dispatcher) {
            try {
                result = forexService?.getForexPrices()
                withContext(Dispatchers.Main) {
                    result?.isSuccessful?.takeIf { false }?.let { isApiError.send(it) }
                }
            } catch (exception: Exception) {
                isApiError.send(true)
            }
        }
        return result?.body()
    }

}