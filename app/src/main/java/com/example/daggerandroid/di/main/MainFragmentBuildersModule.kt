package com.example.daggerandroid.di.main

import com.example.daggerandroid.ui.main.post.PostFragment
import com.example.daggerandroid.ui.main.profile.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributesProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    abstract fun contributesPostFragment(): PostFragment
}