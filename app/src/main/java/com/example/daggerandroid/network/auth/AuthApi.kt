package com.example.daggerandroid.network.auth

import com.example.daggerandroid.model.User
import com.example.daggerandroid.util.Constants
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface AuthApi {

    @GET(Constants.USER)
    fun getUser(@Path("id") id:String): Flowable<User>
}