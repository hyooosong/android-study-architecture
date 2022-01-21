package com.example.moviereview.presenter

import com.example.moviereview.network.MovieResponse

interface ReviewDialogContract {
    interface ReviewDialogView {
        fun showToast(message: String)
    }

    interface ReviewDialogPresenter : BasePresenter<ReviewDialogView> {
        fun updateReviewModel(items: MovieResponse.Item, rating: Float)
        fun hasReview(items: MovieResponse.Item): Boolean
        fun setReviewContent(items: MovieResponse.Item)
    }
}