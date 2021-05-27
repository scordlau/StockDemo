package com.example.network.data.datasource

import com.example.core.data.datamodel.CurrencyListModel
import com.example.core.data.datasource.CurrencyInfoDataSource
import com.example.network.ForexService
import com.example.network.RetrofitModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.util.concurrent.TimeoutException

/**
 * Created by scordlau on 5/24/21.
 */

class NetworkCurrencyInfoDataSource(private val isApiError: Channel<Boolean>) : CurrencyInfoDataSource {

    override suspend fun retrieveAll(): CurrencyListModel? {
        var result: Response<CurrencyListModel>? = null
        withContext(Dispatchers.IO) {
            try {
                val forexService = RetrofitModule<ForexService>()
                        .createApiService(ForexService::class.java, ForexService.baseUrl)
                result = forexService?.getForexPrices()
                withContext(Dispatchers.Main) {
                    result?.isSuccessful?.takeIf { false }?.let { isApiError.send(it) }
                }
            } catch (timeoutException: TimeoutException) {
                isApiError.send(true)
            }
        }
        return result?.body()
    }

}