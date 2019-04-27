package com.example.daggerandroid.di.modules

import com.example.daggerandroid.MainActivity
import com.example.daggerandroid.di.auth.AuthModule
import com.example.daggerandroid.di.auth.AuthViewModelModule
import com.example.daggerandroid.ui.auth.AuthActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    /*
    Just follow the before syntax to inject any activity
     */

    @ContributesAndroidInjector(
        modules = [
            AuthViewModelModule::class,
            AuthModule::class
        ]
    )
    abstract fun contributeAuthActivity(): AuthActivity

    @ContributesAndroidInjector(
        modules = [

        ]
    )
    abstract fun contributeMainActivity(): MainActivity

}