package com.example.daggerandroid.model

import com.google.gson.annotations.SerializedName

class User {
    @SerializedName("email")
    var email: String? = null
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("phone")
    var phone: String? = null
    @SerializedName("username")
    var username: String? = null
    @SerializedName("website")
    var website: String? = null
}


