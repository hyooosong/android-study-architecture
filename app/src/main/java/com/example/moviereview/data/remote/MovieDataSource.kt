package com.example.moviereview.data.remote

interface MovieDataSource {
    suspend fun getMovieList(id: String, pw: String, title: String): MovieResponse
}