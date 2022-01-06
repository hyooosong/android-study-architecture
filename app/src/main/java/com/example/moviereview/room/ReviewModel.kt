package com.example.moviereview.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviereview.network.MovieResponse

@Entity(tableName = "ReviewList")
data class ReviewModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,

    @ColumnInfo(name = "items")
    val items: MovieResponse.Item?,

    @ColumnInfo(name = "rating")
    val rating: Float,

    @ColumnInfo(name = "review")
    val review: String
)