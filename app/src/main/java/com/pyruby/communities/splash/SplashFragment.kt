package com.pyruby.communities.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.pyruby.communities.R
import com.pyruby.communities.api.QueryState
import com.pyruby.communities.databinding.HomeFragmentBinding
import com.pyruby.communities.databinding.SplashFragmentBinding
import com.pyruby.communities.splash.SplashFragmentDirections.Companion.actionSplashFragmentCommunityLoaded
import com.pyruby.communities.ui.main.MainViewModel
import kotlinx.android.synthetic.main.splash_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class SplashFragment : Fragment() {

    private val vmModel: CommunityViewModel by viewModel()
    private lateinit var binding: SplashFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.splash_fragment, container, false)
        binding.customer = vmModel.customer
        binding.viewmodel = vmModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vmModel.communityQuery.observe(viewLifecycleOwner, Observer {
            if (it is QueryState.Success<*>) {
                findNavController().navigate(actionSplashFragmentCommunityLoaded())
            }
        })
        vmModel.loadCommunity()
    }
}