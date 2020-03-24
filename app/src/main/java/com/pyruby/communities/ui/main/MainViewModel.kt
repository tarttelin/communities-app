package com.pyruby.communities.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pyruby.communities.api.CustomerRepository
import com.pyruby.communities.api.QueryState
import com.pyruby.query.CustomerQuery

class MainViewModel(private val customerRepository: CustomerRepository) : ViewModel() {

    val name = MutableLiveData("Findus")
    val errorMessage = MutableLiveData<String>()
    private val _customer: MutableLiveData<QueryState<CustomerQuery.Customer>> = customerRepository.result
    val customer: LiveData<QueryState<CustomerQuery.Customer>> = _customer

    fun getCustomer(primary: Boolean) =
        if (_customer.value == null) {
            customerRepository.getCustomer(primary)
            _customer
        } else {
            _customer
        }


    fun reloadCustomer(primary: Boolean) {
        customerRepository.getCustomer(primary)
    }

    override fun onCleared() {
        customerRepository.clear()
        super.onCleared()
    }
}
