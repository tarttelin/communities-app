package com.pyruby.communities.di.module

import com.pyruby.communities.ui.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vmModule = module {
    viewModel { MainViewModel(get()) }
}