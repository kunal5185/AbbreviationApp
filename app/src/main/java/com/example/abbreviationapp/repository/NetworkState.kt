package com.example.abbreviationapp.repository

import retrofit2.Response

/**
 * This class is sealed class which is used to maintain Success/Error response state from web API.
 */
sealed class NetworkState<out T> {
    data class Success<out T>(val data: T) : NetworkState<T>()
    data class Error<T>(val response: Response<T>) : NetworkState<T>()
}