package com.example.currencydemo.data.datasource

import androidx.lifecycle.MutableLiveData
import com.example.core.data.CurrencyListModel
import com.example.network.ForexService
import com.example.network.RetrofitModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.util.concurrent.TimeoutException

/**
 * Created by scordlau on 5/24/21.
 */

class NetworkCurrencyInfoDataSource(private val isApiError: MutableLiveData<Boolean>) : CurrencyInfoDataSource {

    override suspend fun retrieveAll(): CurrencyListModel? {
        var result: Response<CurrencyListModel>? = null
        withContext(Dispatchers.IO) {
            try {
                val forexService = RetrofitModule<ForexService>()
                        .createApiService(ForexService::class.java, ForexService.baseUrl)
                result = forexService?.getForexPrices()
                withContext(Dispatchers.Main) {
                    isApiError.value = result?.isSuccessful
                }
            } catch (timeoutException: TimeoutException) {
                isApiError.value = true
            }
        }
        return result?.body()
    }

}