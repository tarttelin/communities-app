package com.pyruby.communities.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.pyruby.communities.api.CommunityRepository
import com.pyruby.communities.api.QueryState
import com.pyruby.fragment.Community
import com.pyruby.fragment.Member
import com.pyruby.fragment.Thing

class CommunityViewModel(private val communityRepository: CommunityRepository) : ViewModel() {
    val communityQuery: LiveData<QueryState<Community>> = communityRepository.community

    fun loadCommunity() {
        communityRepository.loadCommunity()
    }

    fun removeThing(thing: Thing) {
        val removeThing = communityRepository.removeThing(thing)
        var removeThingObserver: Observer<QueryState<Member>>? = null
        removeThingObserver = Observer<QueryState<Member>> { t ->
            when(t) {
                is QueryState.Success -> {
                    loadCommunity()
                    removeThing.removeObserver(removeThingObserver!!)
                }
                is QueryState.Failure -> {
                    removeThing.removeObserver(removeThingObserver!!)
                }
            }
        }
        removeThing.observeForever(removeThingObserver)
    }

    override fun onCleared() {
        communityRepository.clear()
        super.onCleared()
    }


}