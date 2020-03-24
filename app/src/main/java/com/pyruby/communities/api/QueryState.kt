package com.pyruby.communities.api

sealed class QueryState<out T> {

    class Loading<out T> : QueryState<T>()
    data class Success<out T>(val data: T?) : QueryState<T>()
    data class Failure<out T>(val cause: String?) : QueryState<T>()
}