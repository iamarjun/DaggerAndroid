package com.example.daggerandroid.di

import android.app.Application
import com.example.daggerandroid.BaseApplication
import com.example.daggerandroid.di.modules.ActivityBuildersModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = [AndroidSupportInjectionModule::class, ActivityBuildersModule::class])
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}