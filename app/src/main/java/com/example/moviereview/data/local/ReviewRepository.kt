package com.example.moviereview.data.local

import com.example.moviereview.data.remote.MovieResponse

interface ReviewRepository {
    suspend fun getList(): List<ReviewModel>
    suspend fun insertList(reviewModel: ReviewModel)
    suspend fun updateReview(items: MovieResponse.Item, changeReview: String)
    suspend fun updateRating(items: MovieResponse.Item, changeRating: Float)
    suspend fun getExistingItems(items: MovieResponse.Item): ReviewModel
    suspend fun hasEntity(items: MovieResponse.Item): Int
}