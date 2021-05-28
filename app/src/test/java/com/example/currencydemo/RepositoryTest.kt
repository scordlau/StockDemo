package com.example.currencydemo

import com.example.core.data.datamodel.CurrencyListModel
import com.example.core.data.datamodel.CurrencyModel
import com.example.core.data.repository.CurrencyInfoRepository
import com.example.network.ForexService
import com.example.network.data.datasource.NetworkCurrencyInfoDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import retrofit2.Response
import java.io.IOException

/**
 * Created by scordlau on 5/27/21.
 */

class RepositoryTest {

    @Test
    fun testApiSuccessEmission() = runBlocking {
        val isApiError = Channel<Boolean>()
        val apiService = mock<ForexService>()
        val testCoroutineDispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(testCoroutineDispatcher)

        val dataSource = NetworkCurrencyInfoDataSource(isApiError, apiService, testCoroutineDispatcher)
        val repo = CurrencyInfoRepository(dataSource, testCoroutineDispatcher)
        val fakeResponse = CurrencyListModel(mapOf(Pair("test", 1.0)))

        `when`(apiService.getForexPrices()).thenReturn(Response.success(fakeResponse))

        val flow = repo.outputFlow
        assert(flow.first()[0].name.equals("test"))
    }

    @Test
    fun testApiFailedEmission() = runBlocking {
        val channel = mock<Channel<Boolean>>()
        val apiService = mock<ForexService>()
        val testCoroutineDispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(testCoroutineDispatcher)

        var sentBool: Boolean = false

        `when`(apiService.getForexPrices()).then { throw IOException() }
        `when`(channel.send(ArgumentMatchers.anyBoolean())).thenAnswer { invocation ->
            sentBool = invocation.arguments[0] as Boolean
            return@thenAnswer Any()
        }

        val dataSource = NetworkCurrencyInfoDataSource(channel, apiService, testCoroutineDispatcher)
        dataSource.retrieveAll()
        assert(sentBool)
    }

    fun Double.isWithinOneRandom(target: Double): Boolean {
        return target - this < 1.0 || target - this < -1.0
    }

    @Test
    fun testRandomBuyRateIsWithIn1() {
        val originalValue = CurrencyModel("test", 1.0, 1.0)

        assert(originalValue.rate.isWithinOneRandom(originalValue.buyRate))
    }

    @Test
    fun testRandomSellRateIsWithIn1() {
        val originalValue = CurrencyModel("test", 1.0, 1.0)

        assert(originalValue.rate.isWithinOneRandom(originalValue.sellRate))
    }

}