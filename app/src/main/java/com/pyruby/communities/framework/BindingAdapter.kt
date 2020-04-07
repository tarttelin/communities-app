package com.pyruby.communities.framework

import android.view.View
import androidx.databinding.BindingAdapter
import com.pyruby.communities.api.QueryState
import kotlinx.android.synthetic.main.splash_fragment.view.*

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("showLoading")
    fun showLoading(view: View, queryState: QueryState<Any>) {
        view.visibility = if (queryState is QueryState.Loading) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("showError")
    fun showError(view: View, queryState: QueryState<Any>) {
        if (queryState is QueryState.Failure) {
            view.visibility = View.VISIBLE
            view.splash_error_text.text = queryState.cause
        } else {
            view.visibility = View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter("showSuccess")
    fun showSuccess(view: View, queryState: QueryState<Any>) {
        view.visibility = if (queryState is QueryState.Success) View.VISIBLE else View.GONE
    }
}
