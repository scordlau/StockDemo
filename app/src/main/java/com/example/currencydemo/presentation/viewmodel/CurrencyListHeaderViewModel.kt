package com.example.currencydemo.presentation.viewmodel

import com.example.currencydemo.R
import com.example.currencydemo.domain.abstraction.AdapterViewModel

/**
 * Created by scordlau on 5/26/21.
 */

class CurrencyListHeaderViewModel() : AdapterViewModel() {
    override fun type(): Int = R.layout.header_currency_list
}