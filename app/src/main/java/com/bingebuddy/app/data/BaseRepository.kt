package com.bingebuddy.app.data

import android.content.Context
import com.bingebuddy.app.network.ApiCallHandler
import com.bingebuddy.app.utils.AsyncResult

abstract class BaseRepository(private val context: Context) {

    protected suspend fun <T> makeSafeApiCall(apiCall: suspend () -> T): AsyncResult<T> {
        return ApiCallHandler.makeSafeApiCall(context, apiCall)
    }
}