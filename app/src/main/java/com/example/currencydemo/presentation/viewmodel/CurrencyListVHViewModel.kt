package com.example.currencydemo.presentation.viewmodel

import com.example.core.data.datamodel.CurrencyModel
import com.example.currencydemo.R
import com.example.currencydemo.domain.abstraction.AdapterViewModel

/**
 * Created by scordlau on 3/23/21.
 */

class CurrencyListVHViewModel(val currencyModel: CurrencyModel, ) : AdapterViewModel() {

    override fun type(): Int = R.layout.item_currency_list

}