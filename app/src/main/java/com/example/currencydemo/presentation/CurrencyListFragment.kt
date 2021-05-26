package com.example.currencydemo.presentation

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.data.CurrencyModel
import com.example.currencydemo.R
import com.example.currencydemo.databinding.FragmentCurrencyListBinding
import com.example.currencydemo.presentation.adapter.CurrencyListAdapter
import com.example.currencydemo.presentation.viewmodel.CurrencyListFragmentViewModel
import com.example.currencydemo.presentation.viewmodel.factory.CurrencyListFragmentViewModelFactory

/**
 * Created by scordlau on 3/23/21.
 */

class CurrencyListFragment() : Fragment() {

    lateinit var viewModel: CurrencyListFragmentViewModel
    lateinit var binding: FragmentCurrencyListBinding

    private val currencyListAdapter = MutableLiveData<CurrencyListAdapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            viewModel = ViewModelProvider(
                    this,
                    CurrencyListFragmentViewModelFactory(
                            it.applicationContext as Application
                    )
            ).get(CurrencyListFragmentViewModel::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentCurrencyListBinding>(
                inflater,
                R.layout.fragment_currency_list,
                container,
                false
        ).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.viewmodel = viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.data.observe(viewLifecycleOwner, Observer<List<CurrencyModel>?> {
            currencyListAdapter.value = CurrencyListAdapter(it)
            binding.recyclerViewCurrencyListFragment.adapter = currencyListAdapter.value
        })
        with(binding.recyclerViewCurrencyListFragment) {
            adapter = currencyListAdapter.value
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(args: Bundle): CurrencyListFragment {
            val fragment = CurrencyListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}