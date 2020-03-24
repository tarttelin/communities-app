package com.pyruby.communities.api

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.request.RequestHeaders
import com.apollographql.apollo.rx2.rxQuery
import com.pyruby.query.CustomerQuery
import io.reactivex.Observable

class ApiService(private val apolloClient: ApolloClient) {

    fun getCustomer(primary: Boolean): Observable<CustomerQuery.Customer> {
        return apolloClient.rxQuery(CustomerQuery(primary)) {
                requestHeaders(RequestHeaders.builder()
                    .addHeader("msisdn", "07770238650")
                    .build()
                )
            }
            .map {
            it.data()?.customer
        }
    }
}