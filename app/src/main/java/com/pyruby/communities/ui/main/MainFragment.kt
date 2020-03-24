package com.pyruby.communities.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.pyruby.communities.R
import com.pyruby.communities.api.QueryState
import com.pyruby.communities.databinding.HomeFragmentBinding
import kotlinx.android.synthetic.main.home_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val vmModel: MainViewModel by viewModel()
    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        binding.name = vmModel.name
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
                    customer_info.text = "Customer: ${it.data?.name?.firstName?: "unknown"}"
                } else if (it is QueryState.Failure) {

                    customer_info.text = "Error occurred: ${it.cause}"
                } else {
                    customer_info.text = "Loading"
                }
            })
        }
    }

}
