package com.example.moviereview.presenter

interface SearchContract {
    interface SearchView {

    }

    interface SearchPresenter : BasePresenter<SearchView> {
        fun callMovieList()
    }
}