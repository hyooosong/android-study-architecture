package com.example.moviereview.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {
    @GET("/v1/search/movie.json")
    fun getMovieList(
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Query("query") query: String
    ): Call<MovieResponse>

    companion object {
        @Volatile
        private var instance: RetrofitService? = null

        fun getInstance(): RetrofitService = instance ?: synchronized(this) {
            instance ?: provideService(RetrofitService::class.java)
                .apply { instance = this }
        }
    }
}