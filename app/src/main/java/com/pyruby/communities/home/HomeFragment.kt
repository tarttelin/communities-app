package com.pyruby.communities.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.pyruby.communities.R
import com.pyruby.communities.api.QueryState
import com.pyruby.communities.databinding.HomeFragmentBinding
import com.pyruby.communities.ui.main.MainViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val vmModel: MainViewModel by viewModel()
    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        binding.customer = vmModel.customer
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vmModel.name.value = "Greetings Bob!"
        load_customer.setOnClickListener {
            val customer = vmModel.getCustomer(false)
            customer.observe(viewLifecycleOwner, Observer {
                if (it is QueryState.Success) {
                    binding.customerInfo.text = "Customer: ${it.data?.name?.firstName?: "unknown"}"
                } else if (it is QueryState.Failure) {
                    binding.customerInfo.text = "Error occurred: ${it.cause}"
                } else {
                    binding.customerInfo.text = "Loading"
                }
            })
        }
    }
}