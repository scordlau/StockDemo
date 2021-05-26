package com.example.network

import com.example.core.data.CurrencyListModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by scordlau on 5/24/21.
 */

interface ForexService {

    @GET("/live")
    suspend fun getForexPrices(@Query("access_key") accessKey: String = secretKey): Response<CurrencyListModel>

    companion object {
        val baseUrl = "http://api.currencylayer.com/"
        val secretKey = "f18295b14744ff0b659437d2f986fac4"
    }

}
