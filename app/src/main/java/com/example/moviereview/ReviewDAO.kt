package com.example.moviereview

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReviewDAO {
    @Query("SELECT * FROM ReviewList")
    fun getList(): List<ReviewModel>

    @Insert
    fun insertList(reviewModel: ReviewModel)
}