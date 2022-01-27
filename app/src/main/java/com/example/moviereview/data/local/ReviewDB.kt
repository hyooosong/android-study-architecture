package com.example.moviereview.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviereview.utils.MovieItemTypeConverter

@Database(entities = [ReviewModel::class], version = 1)
@TypeConverters(MovieItemTypeConverter::class)
abstract class ReviewDB : RoomDatabase() {
    abstract fun reviewDao(): ReviewDAO
}