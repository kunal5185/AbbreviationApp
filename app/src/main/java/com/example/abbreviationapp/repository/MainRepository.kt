package com.example.abbreviationapp.repository

import com.example.abbreviationapp.model.MeaningsData
import com.example.abbreviationapp.retrofit.ApiInterface

/**
 * This class is used for calling web API, which fetches response for abbreviation/sortForm provided by user.
 */
class MainRepository constructor(private val retrofitClient: ApiInterface) {

    suspend fun getMeaningsData(sortForm: String): NetworkState<MeaningsData> {
        val response = retrofitClient.getMeaningsData(sortForm)
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkState.Success(responseBody)
            } else {
                NetworkState.Error(response)
            }
        } else {
            NetworkState.Error(response)
        }
    }
}