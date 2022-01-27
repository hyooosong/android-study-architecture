package com.example.moviereview.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.moviereview.data.remote.MovieResponse

@Dao
interface ReviewDAO {
    @Query("SELECT * FROM ReviewList")
    suspend fun getList(): List<ReviewModel>

    @Insert
    suspend fun insertList(reviewModel: ReviewModel)

    @Query("UPDATE ReviewList SET review = :changeReview WHERE items = :items")
    suspend fun updateReview(items: MovieResponse.Item, changeReview: String)

    @Query("UPDATE ReviewList SET rating = :changeRating WHERE items = :items")
    suspend fun updateRating(items: MovieResponse.Item, changeRating: Float)

    @Query("SELECT * FROM ReviewList WHERE items = :items")
    suspend fun getReviewItems(items: MovieResponse.Item): ReviewModel

    @Query("SELECT count(*) FROM ReviewList WHERE items = :items")
    suspend fun hasEntity(items: MovieResponse.Item): Int
}