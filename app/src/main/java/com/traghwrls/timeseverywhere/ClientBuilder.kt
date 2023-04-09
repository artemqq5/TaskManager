package com.traghwrls.timeseverywhere

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class ClientBuilder {

    val clientRetrofit: API by lazy {
        Retrofit.Builder()
            .baseUrl("https://timezone.abstractapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API::class.java)
    }

    interface API {
        @GET("current_time?api_key=a6324445a5b749af9ed910046b5d2630")
        suspend fun getTimeLocation(
            @Query("location") location: String,
        ): TimeModel?
    }
}