package com.example.currencydemo.presentation.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.NumberFormat
import java.util.*

/**
 * Created by scordlau on 5/26/21.
 */

object TextViewBindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["formatDouble", "formatDoubleSuffix"], requireAll = false)
    fun TextView.formatDouble(value: Double, suffix: String?) {
        val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault())
        numberFormat.minimumFractionDigits = 0
        numberFormat.maximumFractionDigits = 3
        val finalStr: String = numberFormat.format(value)
        val suffixStr = suffix ?: ""
        text = "${finalStr}${suffixStr}"
    }
}