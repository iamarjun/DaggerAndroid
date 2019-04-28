package com.example.daggerandroid.ui.main.post

class PostResource<T>(
    val status: Status,
    val data: T?,
    val message: String?
) {

    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {

        fun <T> success(data: T?): PostResource<T> {
            return PostResource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): PostResource<T> {
            return PostResource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): PostResource<T> {
            return PostResource(Status.LOADING, data, null)
        }
    }
}
