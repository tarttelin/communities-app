package com.pyruby.communities.di.module

import com.pyruby.communities.api.CustomerRepository
import org.koin.dsl.module

val repoModule = module {
    single { CustomerRepository(get()) }
}