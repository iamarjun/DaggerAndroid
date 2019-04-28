package com.example.daggerandroid.di.main

import androidx.lifecycle.ViewModel
import com.example.daggerandroid.di.ViewModelKey
import com.example.daggerandroid.ui.main.post.PostViewModel
import com.example.daggerandroid.ui.main.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PostViewModel::class)
    abstract fun bindPostViewModel(viewModel: PostViewModel): ViewModel
}