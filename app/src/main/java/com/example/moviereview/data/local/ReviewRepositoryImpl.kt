package com.example.moviereview.data.local

import com.example.moviereview.data.remote.MovieResponse
import javax.inject.Inject


class ReviewRepositoryImpl @Inject constructor(private val reviewDao: ReviewDAO) :
    ReviewRepository {

    override suspend fun getList(): List<ReviewModel> {
        return reviewDao.getList()
    }

    override suspend fun insertList(reviewModel: ReviewModel) {
        reviewDao.insertList(reviewModel)
    }

    override suspend fun updateReview(items: MovieResponse.Item, changeReview: String) {
        reviewDao.updateReview(items, changeReview)
    }

    override suspend fun updateRating(items: MovieResponse.Item, changeRating: Float) {
        reviewDao.updateRating(items, changeRating)
    }

    override suspend fun getExistingItems(items: MovieResponse.Item): ReviewModel {
        return reviewDao.getReviewItems(items)
    }

    override suspend fun hasEntity(items: MovieResponse.Item): Int {
        return reviewDao.hasEntity(items)
    }
}