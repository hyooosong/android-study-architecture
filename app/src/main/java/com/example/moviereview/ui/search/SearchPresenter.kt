package com.example.moviereview.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.moviereview.network.MovieResponse
import com.example.moviereview.network.RetrofitService
import com.example.moviereview.network.enqueueListener
import com.example.moviereview.presenter.SearchContract
import retrofit2.Call

class SearchPresenter : SearchContract.SearchPresenter {
    private var searchView: SearchContract.SearchView? = null
    var searchMovie = MutableLiveData<String>()
    var list = listOf<MovieResponse.Item>()
    var isNullEditText = false

    override fun takeView(view: SearchContract.SearchView) {
        searchView = view
    }

    override fun dropView() {
        searchView = null
    }

    override fun callMovieList(listToAdapter: () -> Unit) {
        if (searchMovie.value.isNullOrEmpty()) {
            isNullEditText = true
            return
        }

        val call: Call<MovieResponse> = RetrofitService.getInstance().getMovieList(
            "CqffDKypVaajB6acrJLK",
            "LJ0prk1zCh",
            searchMovie.value!!
        )

        call.enqueueListener(
            onSuccess = {
                list = it.body()!!.items
                listToAdapter.invoke()
            },
            onError = {
                Log.e("ERROR_RETROFIT", it.errorBody().toString())
            }
        )
    }
}