package com.example.daggerandroid.di.main

import com.example.daggerandroid.network.main.MainApi
import com.example.daggerandroid.ui.main.post.PostAdapter
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule {

    @Provides
    fun providePostAdapter(): PostAdapter {
        return PostAdapter()
    }

    @Provides
    fun provideMainApi(retrofit: Retrofit): MainApi {
        return retrofit.create(MainApi::class.java)
    }
}