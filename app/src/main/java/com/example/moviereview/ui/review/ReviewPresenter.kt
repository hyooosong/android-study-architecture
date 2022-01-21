package com.example.moviereview.ui.review

import com.example.moviereview.presenter.ReviewContract
import com.example.moviereview.room.ReviewRepository

class ReviewPresenter(private val reviewRepository: ReviewRepository) :
    ReviewContract.ReviewPresenter {
    private var reviewView: ReviewContract.ReviewView? = null

    override fun takeView(view: ReviewContract.ReviewView) {
        reviewView = view
    }

    override fun dropView() {
        reviewView = null
    }

    override fun setReviewList() {
        val list = reviewRepository.getList()
        reviewView?.listToAdapter(list)
    }
}