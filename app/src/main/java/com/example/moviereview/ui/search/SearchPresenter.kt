package com.example.moviereview.ui.search

import androidx.lifecycle.MutableLiveData
import com.example.moviereview.network.MovieResponse
import com.example.moviereview.network.RetrofitService
import com.example.moviereview.network.enqueueListener
import com.example.moviereview.presenter.SearchContract
import retrofit2.Call

class SearchPresenter : SearchContract.SearchPresenter {
    private var searchView: SearchContract.SearchView? = null
    var searchMovie = MutableLiveData<String>()

    override fun takeView(view: SearchContract.SearchView) {
        searchView = view
    }

    override fun dropView() {
        searchView = null
    }

    override fun callMovieList() {
        if (searchMovie.value.isNullOrEmpty()) {
            searchView?.showToast("검색어를 입력해주세요")
        }

        val call: Call<MovieResponse> = RetrofitService.getInstance().getMovieList(
            "CqffDKypVaajB6acrJLK",
            "LJ0prk1zCh",
            searchMovie.value!!
        )

        call.enqueueListener(
            onSuccess = {
                searchView?.listToAdapter(it.body()?.items)
            },
            onError = {
                searchView?.showToast("다시 시도해주세요")
            }
        )
    }
}