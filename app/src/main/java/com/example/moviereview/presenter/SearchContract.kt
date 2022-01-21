package com.example.moviereview.presenter

import android.app.Application
import com.example.moviereview.network.MovieResponse

interface SearchContract {
    interface SearchView {
        fun listToAdapter(list: List<MovieResponse.Item>?)
        fun showToast(message: String)
        fun showReviewDialog(item: MovieResponse.Item, application: Application)
        fun onClickMore(link: String)
    }

    interface SearchPresenter : BasePresenter<SearchView> {
        fun callMovieList()
    }
}