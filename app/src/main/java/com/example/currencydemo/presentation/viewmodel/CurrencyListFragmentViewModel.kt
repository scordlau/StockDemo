package com.example.currencydemo.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import com.example.core.data.UserProfile
import com.example.core.data.repository.CurrencyInfoRepository
import com.example.core.domain.ViewCurrencyListConcreteCase
import com.example.core.domain.ViewCurrencyListUseCase
import com.example.network.ForexService
import com.example.network.RetrofitModule
import com.example.network.data.datasource.NetworkCurrencyInfoDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * Created by scordlau on 3/23/21.
 */

class CurrencyListFragmentViewModel(app: Application) : AndroidViewModel(app) {

    private val supervisorJob = SupervisorJob()

    private val isApiErrorChannel = Channel<Boolean>()
    val isApiError = isApiErrorChannel.receiveAsFlow().asLiveData()
    private val viewCurrencyListUseCase: ViewCurrencyListUseCase = ViewCurrencyListConcreteCase(
            CurrencyInfoRepository(
                    NetworkCurrencyInfoDataSource(
                            isApiErrorChannel,
                            RetrofitModule<ForexService>().createApiService(ForexService::class.java, ForexService.baseUrl),
                            Dispatchers.IO
                    ),
                    Dispatchers.IO)
            //CurrencyInfoRepository(HardCodeDataSource(), Dispatchers.IO)
    )

    val data = viewCurrencyListUseCase.getCurrencyList().asLiveData()
    val isLoading = Transformations.map(data) {
        if (it != null) viewCurrencyListUseCase.getIsFirstLoad() else true
    }
    val equityData = Transformations.map(data) {
        UserProfile.equity
    }
    val balanceData = Transformations.map(data) {
        UserProfile.balance
    }

    override fun onCleared() {
        super.onCleared()
        supervisorJob.cancelChildren()
    }
}