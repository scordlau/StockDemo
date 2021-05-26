package com.example.currencydemo.domain.abstraction

import androidx.lifecycle.ViewModel

/**
 * Created by scordlau on 5/26/21.
 */

abstract class AdapterViewModel : ViewModel() {

    abstract fun type(): Int

}