package com.example.moviereview.di

import android.content.Context
import androidx.room.Room
import com.example.moviereview.data.local.ReviewDAO
import com.example.moviereview.data.local.ReviewDB
import com.example.moviereview.data.local.ReviewRepository
import com.example.moviereview.data.local.ReviewRepositoryImpl
import com.example.moviereview.utils.MovieItemTypeConverter
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideReviewDB(@ApplicationContext applicationContext: Context): ReviewDB {
        return Room.databaseBuilder(applicationContext, ReviewDB::class.java, "Todo.db")
            .addTypeConverter(MovieItemTypeConverter(Gson()))
            .build()
    }

    @Provides
    @Singleton
    fun provideReviewDAO(reviewDB: ReviewDB): ReviewDAO {
        return reviewDB.reviewDao()
    }

    @Provides
    @Singleton
    fun provideReviewRepository(reviewDAO: ReviewDAO): ReviewRepository {
        return ReviewRepositoryImpl(reviewDAO)
    }
}