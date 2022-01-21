package com.example.moviereview.presenter

import com.example.moviereview.room.ReviewModel

interface ReviewContract {
    interface ReviewView {
        fun listToAdapter(list: List<ReviewModel>?)
    }

    interface ReviewPresenter : BasePresenter<ReviewView> {
        fun setReviewList()
    }
}