package com.example.moviereview

import android.content.Context
import androidx.room.*
import com.google.gson.Gson

@Database(entities = [ReviewModel::class], version = 1)
@TypeConverters(MovieItemTypeConverter::class)
abstract class ReviewDB : RoomDatabase() {
    abstract fun reviewDao(): ReviewDAO

    companion object {
        private val gson = Gson()

        @Volatile
        private var INSTANCE: ReviewDB? = null

        fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, ReviewDB::class.java, "Todo.db")
                .addTypeConverter(MovieItemTypeConverter(gson))
                .build()
    }
}