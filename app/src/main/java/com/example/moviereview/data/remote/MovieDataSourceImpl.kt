package com.example.moviereview.data.remote

import com.example.moviereview.data.remote.service.RetrofitService
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(private val service: RetrofitService) :
    MovieDataSource {
    override suspend fun getMovieList(id: String, pw: String, title: String): MovieResponse {
        return service.getMovieList(id, pw, title)
    }
}