package com.example.moviereview.data.remote.service

import com.example.moviereview.data.remote.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RetrofitService {
    @GET("/v1/search/movie.json")
    suspend fun getMovieList(
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Query("query") query: String
    ): MovieResponse
}