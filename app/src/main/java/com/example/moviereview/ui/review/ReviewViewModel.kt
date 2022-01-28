package com.example.moviereview.ui.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviereview.data.local.ReviewModel
import com.example.moviereview.data.local.ReviewRepository
import com.example.moviereview.data.remote.MovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(private val reviewRepository: ReviewRepository) :
    ViewModel() {
    var editTextReviewContent = MutableLiveData<String>()

    private val _reviewData = MutableLiveData<ReviewModel>()
    val reviewData: LiveData<ReviewModel> = _reviewData

    suspend fun getReviewList() = reviewRepository.getList()

    private suspend fun hasReview(items: MovieResponse.Item) = reviewRepository.hasEntity(items) > 0

    fun updateReview(items: MovieResponse.Item, rating: Float) {
        viewModelScope.launch(Dispatchers.IO) {
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
    }

    fun setExistingContent(items: MovieResponse.Item) {
        viewModelScope.launch(Dispatchers.IO) {
            if (hasReview(items)) {
                withContext(Dispatchers.Main) {
                    _reviewData.value = reviewRepository.getExistingItems(items)
                    editTextReviewContent.value = _reviewData.value?.review
                }
            }
        }
    }
}