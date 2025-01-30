package com.bingebuddy.app.utils

sealed class Async<out T> {
    data object Loading : Async<Nothing>()

    data class Error(val errorMessage: String) : Async<Nothing>()

    data class Success<out T>(val data: T) : Async<T>()
}