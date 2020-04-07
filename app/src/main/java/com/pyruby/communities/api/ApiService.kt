package com.pyruby.communities.api

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.request.RequestHeaders
import com.apollographql.apollo.rx2.rxMutate
import com.apollographql.apollo.rx2.rxQuery
import com.pyruby.fragment.Community
import com.pyruby.fragment.Member
import com.pyruby.query.AddThingMutation
import com.pyruby.query.CommunityQuery
import com.pyruby.query.DeleteThingMutation
import com.pyruby.type.Category
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*

class ApiService(private val apolloClient: ApolloClient) {

    fun getCommunity(): Observable<Community> {
        val auth = String(Base64.getEncoder().encode("${LoggedInUser.user}:pass".toByteArray()))
        return apolloClient.rxQuery(CommunityQuery()) {
            requestHeaders(RequestHeaders.builder()
                .addHeader("Authorization", "Basic $auth")
                .build()
            )

        }.map { it.data()?.community?.fragments?.community}
    }

    fun addThing(name: String, quantity: String, category: Category): Single<Member> {
        val auth = String(Base64.getEncoder().encode("${LoggedInUser.user}:pass".toByteArray()))
        return apolloClient.rxMutate(AddThingMutation(name, quantity, category)) {
            requestHeaders(RequestHeaders.builder()
                .addHeader("Authorization", "Basic $auth")
                .build())
        }.map { it.data()?.member?.fragments?.member }
    }

    fun removeThing(id: String): Single<Member> {
        val auth = String(Base64.getEncoder().encode("${LoggedInUser.user}:pass".toByteArray()))
        return apolloClient.rxMutate(DeleteThingMutation(id)) {
            requestHeaders(RequestHeaders.builder()
                .addHeader("Authorization", "Basic $auth")
                .build())
        }.map { it.data()?.removeThing?.fragments?.member }
    }
}