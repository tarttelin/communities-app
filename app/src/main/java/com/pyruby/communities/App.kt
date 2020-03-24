package com.pyruby.communities

import android.app.Application
import com.pyruby.communities.di.module.appModule
import com.pyruby.communities.di.module.networkModule
import com.pyruby.communities.di.module.repoModule
import com.pyruby.communities.di.module.vmModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            fragmentFactory()
            modules(listOf(vmModule, appModule, networkModule, repoModule))
        }
    }
}