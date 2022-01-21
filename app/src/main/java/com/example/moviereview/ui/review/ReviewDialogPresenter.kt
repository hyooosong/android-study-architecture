package com.example.moviereview.ui.review

import androidx.lifecycle.MutableLiveData
import com.example.moviereview.network.MovieResponse
import com.example.moviereview.presenter.ReviewDialogContract
import com.example.moviereview.room.ReviewModel
import com.example.moviereview.room.ReviewRepository

class ReviewDialogPresenter(private val reviewRepository: ReviewRepository) : ReviewDialogContract.ReviewDialogPresenter {
    private var reviewDialogView: ReviewDialogContract.ReviewDialogView? = null
    var reviewData: ReviewModel? = null
    var reviewContent = MutableLiveData<String>()

    override fun takeView(view: ReviewDialogContract.ReviewDialogView) {
        reviewDialogView = view
    }

    override fun dropView() {
        reviewDialogView = null
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

        reviewDialogView?.showToast("리뷰가 등록되었습니다")
    }

    override fun hasReview(items: MovieResponse.Item) =
        reviewRepository.hasEntity(items) > 0

    override fun setReviewContent(items: MovieResponse.Item) {
        if (hasReview(items)) {
            reviewData = reviewRepository.getReviewItems(items)
            reviewContent.value = reviewData?.review
        }
    }
}