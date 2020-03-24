package com.pyruby.communities.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pyruby.query.CustomerQuery
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CustomerRepository(private val apiService: ApiService) {
    private val compositeDisposable = CompositeDisposable()
    val result = MutableLiveData<QueryState<CustomerQuery.Customer>>()

    fun getCustomer(primary: Boolean): MutableLiveData<QueryState<CustomerQuery.Customer>> {
        addDisposable(
            apiService.getCustomer(primary)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { whenStart() }
                .subscribe(
                    { result -> whenSuccess(result) },
                    { cause -> whenError(cause.toString()) }
                )
        )
        return result
    }

    private fun whenStart() {
        result.value = QueryState.Loading()
    }

    fun whenSuccess(customer: CustomerQuery.Customer) {
        result.value = QueryState.Success(customer)
    }

    fun whenError(cause: String) {
        result.value = QueryState.Failure(cause)
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun clear() {
        compositeDisposable.clear()
    }
}