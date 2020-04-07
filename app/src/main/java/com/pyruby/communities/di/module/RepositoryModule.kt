package com.pyruby.communities.di.module

import com.pyruby.communities.api.CommunityRepository
import org.koin.dsl.module

val repoModule = module {
    single { CommunityRepository(get()) }
}