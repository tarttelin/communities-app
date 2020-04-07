package com.pyruby.communities.di.module

import androidx.navigation.fragment.NavHostFragment
import com.pyruby.communities.home.HomeFragment
import com.pyruby.communities.splash.SplashFragment
import com.pyruby.communities.thing.AddThingFragment
import com.pyruby.communities.thing.ThingListFragment
import org.koin.androidx.fragment.dsl.fragment
import org.koin.dsl.module

val appModule = module {
    fragment { SplashFragment() }
    fragment { HomeFragment() }
    fragment { ThingListFragment() }
    fragment { NavHostFragment() }
    fragment { AddThingFragment() }
}