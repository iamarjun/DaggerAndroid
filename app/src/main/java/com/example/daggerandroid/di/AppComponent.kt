package com.example.daggerandroid.di

import android.app.Application
import com.example.daggerandroid.BaseApplication
import com.example.daggerandroid.di.modules.ActivityBuildersModule
import com.example.daggerandroid.di.modules.AppModule
import com.example.daggerandroid.di.modules.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuildersModule::class,
        AppModule::class,
        ViewModelFactoryModule::class
    ]
)
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}