package com.example.currencydemo.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.core.data.CurrencyListModel
import com.example.core.data.CurrencyModel
import com.example.core.data.UserProfile
import com.example.currencydemo.data.datasource.FakeNetworkRandomDataSource
import com.example.currencydemo.data.datasource.HardCodeDataSource
import com.example.currencydemo.data.datasource.NetworkCurrencyInfoDataSource
import domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by scordlau on 3/22/21.
 */

class CurrencyInfoRepository : Repository<CurrencyListModel> {

    var localData = LinkedHashMap<String, CurrencyModel>()
    private val isApiError = MutableLiveData<Boolean>()
    val isFirstsLoad = AtomicBoolean(true)

    private val apiFlow: Flow<CurrencyListModel?> = flow {
        val dataSource = NetworkCurrencyInfoDataSource(isApiError)
        val fakeRandomChangeSource = FakeNetworkRandomDataSource(localData)
        while (true) {
            //val result = if (isFirstsLoad.getAndSet(false)) dataSource.retrieveAll() else fakeRandomChangeSource.retrieveAll()
            val result = if (isFirstsLoad.getAndSet(false)) HardCodeDataSource().retrieveAll() else fakeRandomChangeSource.retrieveAll()
            emit(result)
            delay(REFRESH_INTERVAL)
        }
    }.flowOn(Dispatchers.IO)

    val outputFlow = apiFlow.map {
        updateLocalData(it)
        localData.map { it.value }.toList()
    }.onEach {
        updateUserProfile(it)
    }

    private fun updateUserProfile(data: List<CurrencyModel>) {
        UserProfile.updateProfileBalance(data?.size ?: 0)
        UserProfile.updateProfileEquity(data)
    }

    @Synchronized
    private fun updateLocalData(data: CurrencyListModel?) {
        if (localData.size == 0) {
            val map = data?.quotes?.entries?.associate { it.key to CurrencyModel(it.key, it.value, it.value) }
            map?.let { localData.putAll(it) }
        } else {
            transverseData(data)
        }
    }

    private fun transverseData(data: CurrencyListModel?) {
        data?.quotes?.forEach { (name, value) ->
            localData.get(name)?.updateRate(value)?.let {
                localData.put(name, it)
            }
        }
    }

    override suspend fun retrieveAll() {
        /*
        val dataSource = NetworkCurrencyInfoDataSource(isApiError)
        withContext(Dispatchers.Main) {
            val result = dataSource.retrieveAll()
            apiData.value = result
        }

         */
    }

    companion object {
        const val REFRESH_INTERVAL = 10000L
    }

}