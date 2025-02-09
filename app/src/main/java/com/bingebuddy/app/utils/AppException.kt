package com.bingebuddy.app.utils

open class AppException(message: String? = null, cause: Throwable? = null) :
    Exception(message, cause)


/*
 * Exception for no internet connection
 */
class NoInternetException(message: String? = null, cause: Throwable? = null) :
    AppException(message, cause)


/*
 * Exception for invalid credentials
 */
class InvalidCredentialsException(message: String? = null, cause: Throwable? = null) :
    AppException(message, cause)


/*
 * Exception for invalid api key
 */
class ApiException(message: String? = null, cause: Throwable? = null) : AppException(message, cause)


/*
 * Exception for invalid api key
 */
class NetworkException(message: String? = null, cause: Throwable? = null) :
    AppException(message, cause)


/*
 * Exception for all other related
 */
class UnknownException(message: String? = null, cause: Throwable? = null) :
    AppException(message, cause)