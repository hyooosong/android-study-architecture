package com.example.moviereview

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReviewDAO {
    @Query("SELECT * FROM ReviewList")
    fun getList(): LiveData<List<ReviewModel>>

    @Insert
    fun insertList(reviewModel: ReviewModel)
}