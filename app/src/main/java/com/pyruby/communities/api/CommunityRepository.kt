package com.pyruby.communities.api

import androidx.lifecycle.MutableLiveData
import com.pyruby.fragment.Community
import com.pyruby.fragment.Member
import com.pyruby.fragment.Thing
import com.pyruby.type.Category
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CommunityRepository(private val apiService: ApiService) {
    private val compositeDisposable = CompositeDisposable()
    val community = MutableLiveData<QueryState<Community>>()
    val addedThingToMember = MutableLiveData<QueryState<Member>>()
    val removedThingFromMember = MutableLiveData<QueryState<Member>>()

    fun loadCommunity(): MutableLiveData<QueryState<Community>> {
        addDisposable(
            apiService.getCommunity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { whenStart(community) }
                .subscribe(
                    { result -> whenSuccess(community, result) },
                    { cause -> whenError(community, cause.toString()) }
                )
        )
        return community
    }

    fun addThing(name: String, quantity: String, category: Category): MutableLiveData<QueryState<Member>> {
        addDisposable(
            apiService.addThing(name, quantity, category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { whenStart(addedThingToMember)}
                .subscribe(
                    { result -> whenSuccess(addedThingToMember, result) },
                    { cause -> whenError(addedThingToMember, cause.toString()) }
                )
        )
        return addedThingToMember
    }

    fun removeThing(thing: Thing): MutableLiveData<QueryState<Member>> {
        addDisposable(
            apiService.removeThing(thing.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { whenStart(removedThingFromMember)}
                .subscribe(
                    { result -> whenSuccess(removedThingFromMember, result) },
                    { cause -> whenError(removedThingFromMember, cause.toString()) }
                )
        )
        return removedThingFromMember
    }

    private fun <T> whenStart(holder: MutableLiveData<QueryState<T>>) {
        holder.value = QueryState.Loading()
    }

    fun <T> whenSuccess(holder: MutableLiveData<QueryState<T>>, result: T) {
        holder.value = QueryState.Success(result)
    }

    fun <T> whenError(holder: MutableLiveData<QueryState<T>>,cause: String) {
        holder.value = QueryState.Failure(cause)
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun clear() {
        compositeDisposable.clear()
    }
}