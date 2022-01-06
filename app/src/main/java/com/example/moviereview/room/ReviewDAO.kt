package com.example.moviereview.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.moviereview.network.MovieResponse

@Dao
interface ReviewDAO {
    @Query("SELECT * FROM ReviewList")
    fun getList(): List<ReviewModel>

    @Insert
    fun insertList(reviewModel: ReviewModel)

    @Query("UPDATE ReviewList SET review = :changeReview WHERE items = :items")
    fun updateList(items: MovieResponse.Item, changeReview: String)

    @Query("SELECT * FROM ReviewList WHERE items = :items")
    fun getItems(items: MovieResponse.Item) : ReviewModel

    @Query("SELECT count(*) FROM ReviewList WHERE items = :items")
    fun hasEntity(items: MovieResponse.Item): Int
}