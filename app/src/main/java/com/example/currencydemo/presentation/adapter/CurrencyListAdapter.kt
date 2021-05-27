package com.example.currencydemo.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.core.data.datamodel.CurrencyModel
import com.example.currencydemo.R
import com.example.currencydemo.databinding.ItemCurrencyListBinding
import com.example.currencydemo.domain.abstraction.AdapterViewModel
import com.example.currencydemo.presentation.adapter.viewholder.CurrencyHeaderHolder
import com.example.currencydemo.presentation.adapter.viewholder.CurrencyListHolder
import com.example.currencydemo.presentation.adapter.viewholder.CurrencyListViewHolder
import com.example.currencydemo.presentation.viewmodel.CurrencyListHeaderViewModel
import com.example.currencydemo.presentation.viewmodel.CurrencyListVHViewModel

/**
 * Created by scordlau on 3/23/21.
 */

class CurrencyListAdapter(
        data: List<CurrencyModel>?
) : RecyclerView.Adapter<CurrencyListHolder<ViewModel>>() {

    private var viewModels = mutableListOf<AdapterViewModel>()

    init {
        viewModels.add(0, CurrencyListHeaderViewModel())
        data?.map { CurrencyListVHViewModel(it) }
                ?.toMutableList()
                ?.let { viewModels.addAll(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyListHolder<ViewModel> {
        return if (viewType == R.layout.item_currency_list) {
            val binding = DataBindingUtil.inflate<ItemCurrencyListBinding>(
                    LayoutInflater.from(parent.context),
                    viewType,
                    parent,
                    false
            ).also {
                it.lifecycleOwner = parent.context as? LifecycleOwner
            }
            CurrencyListViewHolder(binding) as CurrencyListHolder<ViewModel>
        } else {
            val view = LayoutInflater.from(parent.context)
                    .inflate(viewType, parent, false)
            CurrencyHeaderHolder(view) as CurrencyListHolder<ViewModel>
        }
    }

    override fun getItemCount(): Int = viewModels.size

    override fun getItemViewType(position: Int): Int {
        return viewModels[position].type()
    }

    override fun onBindViewHolder(holder: CurrencyListHolder<ViewModel>, position: Int) {
        if (getItemViewType(position) == R.layout.item_currency_list) viewModels[position].let { holder.bind(it) }
    }

}