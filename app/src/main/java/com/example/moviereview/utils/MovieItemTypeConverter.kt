package com.example.moviereview.utils

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.moviereview.data.remote.MovieResponse
import com.google.gson.Gson

@ProvidedTypeConverter
class MovieItemTypeConverter(private val gson: Gson) {
    @TypeConverter
    fun listToJson(value: MovieResponse.Item) = gson.toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = gson.fromJson(value, MovieResponse.Item::class.java)
}