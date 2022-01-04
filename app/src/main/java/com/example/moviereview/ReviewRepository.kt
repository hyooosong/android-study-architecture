package com.example.moviereview

import android.app.Application

class ReviewRepository(application: Application) {
    private var reviewDB = ReviewDB.getInstance(application)
    private var reviewDao = reviewDB.reviewDao()
    private var reviewList = reviewDao.getList()

    fun getList() = reviewList

    fun insertList(reviewModel: ReviewModel) {
        Thread(Runnable {
            reviewDao.insertList(reviewModel)
        }).start()
    }
}