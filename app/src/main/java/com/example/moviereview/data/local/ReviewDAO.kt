package com.example.moviereview.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.moviereview.data.remote.MovieResponse

@Dao
interface ReviewDAO {
    @Query("SELECT * FROM ReviewList")
    fun getList(): List<ReviewModel>

    @Insert
    fun insertList(reviewModel: ReviewModel)

    @Query("UPDATE ReviewList SET review = :changeReview WHERE items = :items")
    fun updateReview(items: MovieResponse.Item, changeReview: String)

    @Query("UPDATE ReviewList SET rating = :changeRating WHERE items = :items")
    fun updateRating(items: MovieResponse.Item, changeRating: Float)

    @Query("SELECT * FROM ReviewList WHERE items = :items")
    fun getReviewItems(items: MovieResponse.Item): ReviewModel

    @Query("SELECT count(*) FROM ReviewList WHERE items = :items")
    fun hasEntity(items: MovieResponse.Item): Int
}