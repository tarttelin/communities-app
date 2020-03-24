package com.pyruby.communities.ui

import android.view.View
import androidx.databinding.BindingAdapter
import com.pyruby.communities.api.QueryState

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("visibleLoading")
    fun showLoading(view: View, queryState: QueryState<Any>) {
        println("Show loader component: ${queryState is QueryState.Loading}")
        view.visibility = if (queryState is QueryState.Loading) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("visibleError")
    fun showError(view: View, queryState: QueryState<Any>) {
        println("Show error component: ${queryState is QueryState.Failure}")
        view.visibility = if (queryState is QueryState.Failure) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("visibleSuccess")
    fun showSuccess(view: View, queryState: QueryState<Any>) {

        view.visibility = if (queryState is QueryState.Success) View.VISIBLE else View.GONE
    }
}
