package com.pyruby.communities.di.module

import com.apollographql.apollo.ApolloClient
import com.pyruby.communities.api.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module


private val okHttpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build()
private val apolloClient = ApolloClient.builder()
    .serverUrl("http://10.0.2.2:8080/graphql")
    .okHttpClient(okHttpClient)
    .build()
private val apiService = ApiService(apolloClient)

val networkModule = module {
    single { apiService }
}