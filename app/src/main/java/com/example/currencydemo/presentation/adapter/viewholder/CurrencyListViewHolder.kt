package com.example.currencydemo.presentation.adapter.viewholder

import com.example.currencydemo.databinding.ItemCurrencyListBinding
import com.example.currencydemo.presentation.viewmodel.CurrencyListVHViewModel

/**
 * Created by scordlau on 5/26/21.
 */

class CurrencyListViewHolder(private val binding: ItemCurrencyListBinding) : CurrencyListHolder<CurrencyListVHViewModel>(binding.root) {

    override fun bind(viewModel: CurrencyListVHViewModel) {
        binding.viewModel = viewModel
    }

}