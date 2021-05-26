package com.example.currencydemo.domain.abstraction

import com.example.currencydemo.presentation.viewmodel.CurrencyListHeaderViewModel
import com.example.currencydemo.presentation.viewmodel.CurrencyListVHViewModel

/**
 * Created by scordlau on 5/26/21.
 */

interface TypeFactory {

    fun type(visitable: CurrencyListVHViewModel): Int
    fun type(visitable: CurrencyListHeaderViewModel): Int

}