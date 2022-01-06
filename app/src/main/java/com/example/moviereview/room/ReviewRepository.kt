package com.example.moviereview.room

import android.app.Application
import com.example.moviereview.network.MovieResponse
import com.example.moviereview.room.ReviewDB
import com.example.moviereview.room.ReviewModel

class ReviewRepository(application: Application) {
    private var reviewDB = ReviewDB.getInstance(application)
    private var reviewDao = reviewDB.reviewDao()

    fun getList(): List<ReviewModel> = reviewDao.getList()

    fun insertList(reviewModel: ReviewModel) {
        Thread(Runnable {
            reviewDao.insertList(reviewModel)
        }).start()
    }

    fun updateList(items: MovieResponse.Item, changeReview: String) {
        Thread(Runnable {
            reviewDao.updateList(items, changeReview)
        }).start()
    }

    fun getItems(items: MovieResponse.Item) : ReviewModel? {
        var entity : ReviewModel? = null
        val th = Thread(Runnable {
            entity = reviewDao.getItems(items)
        })
        th.start()
        th.join()
        return entity
    }

    fun hasEntity(items: MovieResponse.Item): Int {
        var count = 0
        val th = Thread(Runnable {
            count = reviewDao.hasEntity(items)
        })
        th.start()
        th.join()
        return count
    }
}