package com.example.currencydemo.presentation.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by scordlau on 5/26/21.
 */

abstract class CurrencyListHolder<in T>(view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(viewModel: T) {}
}