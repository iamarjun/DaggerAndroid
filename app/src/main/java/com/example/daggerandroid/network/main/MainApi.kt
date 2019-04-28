package com.example.daggerandroid.network.main

import com.example.daggerandroid.model.Post
import com.example.daggerandroid.util.Constants
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface MainApi {

    @GET(Constants.POST)
    fun getPosts(@Query("userId") userId: String): Flowable<List<Post>>
}