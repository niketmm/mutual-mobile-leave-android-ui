package com.mutualmobile.mmleave.util

sealed class LandingPageState<T> {
    class Loading<T> : LandingPageState<T>()
    class Success<T>(val data: T) : LandingPageState<T>()
    class Failed<T>(val message: String) : LandingPageState<T>()

    val isLoading get() = this is Loading

    val isSuccess get() = this is Success

    val isFailed get() = this is Failed

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success(data)
        fun <T> failed(message: String) = Failed<T>(message)
    }
}