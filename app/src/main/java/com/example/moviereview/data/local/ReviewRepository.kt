package com.example.moviereview.data.local

import android.app.Application
import com.example.moviereview.data.remote.MovieResponse

class ReviewRepository(application: Application) {
    private var reviewDB = ReviewDB.getInstance(application)
    private var reviewDao = reviewDB.reviewDao()

    fun getList(): List<ReviewModel>? {
        var list: List<ReviewModel>? = null
        val th = Thread {
            list = reviewDao.getList()
        }
        th.start()
        th.join()
        return list
    }

    fun insertList(reviewModel: ReviewModel) {
        Thread {
            reviewDao.insertList(reviewModel)
        }.start()
    }

    fun updateReview(items: MovieResponse.Item, changeReview: String) {
        Thread {
            reviewDao.updateReview(items, changeReview)
        }.start()
    }

    fun updateRating(items: MovieResponse.Item, changeRating: Float) {
        Thread {
            reviewDao.updateRating(items, changeRating)
        }.start()
    }

    fun getExistingItems(items: MovieResponse.Item): ReviewModel? {
        var entity: ReviewModel? = null
        val th = Thread {
            entity = reviewDao.getReviewItems(items)
        }
        th.start()
        th.join()
        return entity
    }

    fun hasEntity(items: MovieResponse.Item): Int {
        var count = 0
        val th = Thread {
            count = reviewDao.hasEntity(items)
        }
        th.start()
        th.join()
        return count
    }
}