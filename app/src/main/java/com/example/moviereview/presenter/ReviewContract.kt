package com.example.moviereview.presenter

import com.example.moviereview.network.MovieResponse
import com.example.moviereview.room.ReviewModel

interface ReviewContract {
    interface ReviewView {

    }

    interface ReviewPresenter : BasePresenter<ReviewView> {
        fun hasReview(items: MovieResponse.Item): Boolean
        fun setReviewContent(items: MovieResponse.Item)
        fun updateReviewModel(items: MovieResponse.Item, rating: Float)
        fun setReviewList(): List<ReviewModel>?
    }
}