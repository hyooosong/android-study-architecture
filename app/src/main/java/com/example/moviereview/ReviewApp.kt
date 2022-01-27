package com.example.moviereview

import android.app.Application
import androidx.room.Room
import com.example.moviereview.data.local.ReviewDB
import com.example.moviereview.utils.MovieItemTypeConverter
import com.google.gson.Gson

class ReviewApp : Application() {
    companion object {
        lateinit var reviewDB: ReviewDB
    }

    override fun onCreate() {
        super.onCreate()
        reviewDB = Room.databaseBuilder(applicationContext, ReviewDB::class.java, "Todo.db")
            .addTypeConverter(MovieItemTypeConverter(Gson()))
            .build()
    }
}