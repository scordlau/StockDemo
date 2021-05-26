package com.example.currencydemo.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import com.example.core.data.UserProfile
import com.example.currencydemo.data.repository.CurrencyInfoRepository
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren

/**
 * Created by scordlau on 3/23/21.
 */

class CurrencyListFragmentViewModel(app: Application) : AndroidViewModel(app) {

    private val supervisorJob = SupervisorJob()
    private val repository = CurrencyInfoRepository()

    val data = repository.outputFlow.asLiveData()
    val isLoading = Transformations.map(data) {
        if (it != null) repository.isFirstsLoad.get() else true
    }
    val equityData = Transformations.map(data) {
        UserProfile.equity
    }
    val balanceData = Transformations.map(data) {
        UserProfile.balance
    }

    init {
        fetchDataFromRemote()
    }

    private fun fetchDataFromRemote() {
        /*
        isLoading.value = true
        CoroutineScope(supervisorJob + Dispatchers.IO).launch {
            repository.retrieveAll()
            withContext(Dispatchers.Main) {
                isLoading.value = false
            }
        }

         */
    }

    override fun onCleared() {
        super.onCleared()
        supervisorJob.cancelChildren()
    }
}