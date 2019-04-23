package com.example.daggerandroid.di.modules

import com.example.daggerandroid.AuthActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    /*
    Just follow the before syntax to inject any activity
     */

    @ContributesAndroidInjector
    abstract fun contributeAuthActivity(): AuthActivity

}