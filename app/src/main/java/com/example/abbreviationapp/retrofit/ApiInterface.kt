package com.example.abbreviationapp.retrofit

import com.example.abbreviationapp.model.MeaningsData
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("dictionary.py")
    suspend fun getMeaningsData(
        @Query("sf") sortForm: String
    ): Response<MeaningsData>

    companion object {
        private const val BASE_URL = "http://www.nactem.ac.uk/software/acromine/"
        private var retrofitService: ApiInterface? = null
        fun getInstance(): ApiInterface {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(ApiInterface::class.java)
            }
            return retrofitService!!
        }
    }
}