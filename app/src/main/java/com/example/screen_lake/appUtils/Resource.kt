package com.example.screen_lake.appUtils

sealed class Resource<out T>{
    class Success<out T>(val data: T?) : Resource<T>()
    class Error<out T>(message: String, data: T? = null) : Resource<T>()
    class Loading<out T>(data: T? = null) : Resource<T>()
}
