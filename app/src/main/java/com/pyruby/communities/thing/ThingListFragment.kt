package com.pyruby.communities.thing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.pyruby.communities.api.LoggedInUser
import com.pyruby.communities.api.QueryState
import com.pyruby.communities.databinding.ThingsListFragmentBinding
import com.pyruby.communities.home.CommunityViewModel
import com.pyruby.fragment.Community
import io.reactivex.disposables.Disposable
import org.koin.android.viewmodel.ext.android.viewModel

class ThingListFragment : Fragment() {

    private val vmModel: CommunityViewModel by viewModel()
    private var subscribeToUpdates: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = ThingsListFragmentBinding.inflate(inflater, container, false)
        val adapter = ThingAdapter(vmModel)
        binding.thingsList.adapter = adapter
        bindAdapter(adapter)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        vmModel.loadCommunity()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        subscribeToUpdates?.dispose()
        subscribeToUpdates = null
    }

    private fun bindAdapter(adapter: ThingAdapter) {
        vmModel.communityQuery.observe(viewLifecycleOwner, Observer {
            when (it) {
                is QueryState.Success<Community> -> {
                    val myThings = it.data?.households?.edges?.flatMap { it?.node?.fragments?.household?.members?.edges ?: emptyList() }
                        ?.filterNotNull()
                        ?.filter { memberEdge -> memberEdge.node.fragments.member.userId == LoggedInUser.user }
                        ?.flatMap { loggedInUser -> loggedInUser.node.fragments.member.things.edges ?: emptyList() }
                        ?.map { thingEdge -> thingEdge?.node?.fragments?.thing }
                    adapter.submitList(myThings)
                }
            }
        })
    }
}