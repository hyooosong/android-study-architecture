package com.example.moviereview.di

import com.example.moviereview.data.remote.MovieDataSource
import com.example.moviereview.data.remote.MovieDataSourceImpl
import com.example.moviereview.data.remote.service.RetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object DataSourceModule {

    @Provides
    @ActivityRetainedScoped
    fun provideDataSource(service: RetrofitService): MovieDataSource {
        return MovieDataSourceImpl(service)
    }
}