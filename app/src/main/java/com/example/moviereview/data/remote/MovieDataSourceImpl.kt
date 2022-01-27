package com.example.moviereview.data.remote

class MovieDataSourceImpl : MovieDataSource {
    override suspend fun getMovieList(id: String, pw: String, title: String): MovieResponse {
        return NetworkModule.providerService(NetworkModule.provideRetrofit())
            .getMovieList(id, pw, title)
    }
}