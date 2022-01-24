package com.example.moviereview.ui.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviereview.data.local.ReviewModel
import com.example.moviereview.data.local.ReviewRepository
import com.example.moviereview.data.remote.MovieResponse

class ReviewViewModel(private val reviewRepository: ReviewRepository) : ViewModel() {
    var editTextReviewContent = MutableLiveData<String>()

    private val _reviewData = MutableLiveData<ReviewModel>()
    val reviewData: LiveData<ReviewModel> = _reviewData

    private fun hasReview(items: MovieResponse.Item) = reviewRepository.hasEntity(items) > 0

    fun updateReview(items: MovieResponse.Item, rating: Float) {
        if (hasReview(items)) {
            reviewRepository.updateRating(items, rating)
            reviewRepository.updateReview(items, editTextReviewContent.value.toString())
        } else {
            reviewRepository.insertList(
                ReviewModel(
                    null, items, rating, editTextReviewContent.value.toString()
                )
            )
        }
    }

    fun setExistingContent(items: MovieResponse.Item) {
        if (hasReview(items)) {
            _reviewData.value = reviewRepository.getExistingItems(items)
            editTextReviewContent.value = _reviewData.value?.review
        }
    }
}