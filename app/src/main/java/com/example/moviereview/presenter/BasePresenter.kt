package com.example.moviereview.presenter

interface BasePresenter<T> {
    fun takeView(view: T)
    fun dropView()
}