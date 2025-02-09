package com.bingebuddy.app.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import coil3.network.HttpException
import com.bingebuddy.app.utils.ApiException
import com.bingebuddy.app.utils.AppException
import com.bingebuddy.app.utils.AsyncResult
import com.bingebuddy.app.utils.NetworkException
import com.bingebuddy.app.utils.NoInternetException
import com.bingebuddy.app.utils.UnknownException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

object ApiCallHandler {
    suspend fun <T> makeSafeApiCall(
        context: Context,
        apiCall: suspend () -> T

    ): AsyncResult<T> = withContext(Dispatchers.IO) {
        try {
            AsyncResult.Success(apiCall.invoke())
        } catch (e: Exception) {
            val mappedException: AppException = when (e) {
                is IOException -> {
                    if (!isNetworkAvailable(context)) {
                        NoInternetException("No internet connection", e)
                    } else if (e is ConnectException || e is SocketTimeoutException) {
                        ApiException(message = "Something went wrong!!", e)
                    } else {
                        NetworkException("Network error", e)

                    }
                }

                is HttpException -> {
                    val errorMessage = when (val errorCode = e.response.code) {
                        401 -> "Unauthorized"
                        403 -> "Forbidden"
                        404 -> "Not Found"
                        in 500..509 -> "Internal Server Error"
                        else -> "HTTP Error $errorCode"
                    }
                    ApiException(errorMessage, e)
                }

                is CancellationException -> {
                    throw e

                }

                is AppException -> {
                    e
                }

                else -> {
                    UnknownException("Unknown error", e)
                }

            }
            AsyncResult.Failure(mappedException)
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) && capabilities.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_VALIDATED
            )
        }

        return false
    }
}