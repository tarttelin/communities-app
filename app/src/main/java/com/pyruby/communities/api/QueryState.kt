package com.pyruby.communities.api

sealed class QueryState<out T>(val value: T?) {

    class Loading<out T> : QueryState<T>(null)
    data class Success<out T>(val data: T?) : QueryState<T>(data)
    data class Failure<out T>(val cause: String?) : QueryState<T>(null)
}