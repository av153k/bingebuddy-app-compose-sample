package com.bingebuddy.app.utils

sealed class AsyncResult<out T> {
    data class Failure(val exception: AppException) : AsyncResult<Nothing>()
    data class Success<out T>(val data: T): AsyncResult<T>()
}