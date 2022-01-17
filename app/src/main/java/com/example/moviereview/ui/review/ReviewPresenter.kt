package com.example.moviereview.ui.review

import androidx.lifecycle.MutableLiveData
import com.example.moviereview.network.MovieResponse
import com.example.moviereview.presenter.ReviewContract
import com.example.moviereview.room.ReviewModel
import com.example.moviereview.room.ReviewRepository

class ReviewPresenter(private val reviewRepository: ReviewRepository) :
    ReviewContract.ReviewPresenter {
    private var reviewView: ReviewContract.ReviewView? = null
    var reviewData: ReviewModel? = null
    var reviewContent = MutableLiveData<String>()

    override fun takeView(view: ReviewContract.ReviewView) {
        reviewView = view
    }

    override fun dropView() {
        reviewView = null
    }

    override fun hasReview(items: MovieResponse.Item) =
        reviewRepository.hasEntity(items) > 0

    override fun setReviewContent(items: MovieResponse.Item) {
        if (hasReview(items)) {
            reviewData = reviewRepository.getReviewItems(items)
            reviewContent.value = reviewData?.review
        }
    }

    override fun updateReviewModel(
        items: MovieResponse.Item, rating: Float
    ) {
        if (hasReview(items)) {
            reviewRepository.updateRating(items, rating)
            reviewRepository.updateReview(items, reviewContent.value.toString())
        } else {
            reviewRepository.insertList(
                ReviewModel(
                    null, items, rating, reviewContent.value.toString()
                )
            )
        }
    }

    override fun setReviewList() = reviewRepository.getList()
}