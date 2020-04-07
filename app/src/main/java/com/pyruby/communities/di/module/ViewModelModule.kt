package com.pyruby.communities.di.module

import com.pyruby.communities.home.CommunityViewModel
import com.pyruby.communities.thing.AddThingViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vmModule = module {
    viewModel { CommunityViewModel(get()) }
    viewModel { AddThingViewModel(get()) }
}