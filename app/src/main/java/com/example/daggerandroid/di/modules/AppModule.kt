package com.example.daggerandroid.di.modules

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.daggerandroid.R
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideRequestOptions(): RequestOptions {
        return RequestOptions
            .placeholderOf(R.drawable.jetpack)
            .error(R.drawable.background)
    }

    @Provides
    fun provideGlideInstance(application: Application, requestOptions: RequestOptions): RequestManager {
        return Glide.with(application)
            .setDefaultRequestOptions(requestOptions)
    }

    @Provides
    fun provideAppDrawble(application: Application): Drawable {
        return ContextCompat.getDrawable(application, R.drawable.jetpack)!!
    }

}