package com.example.moviereview.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviereview.data.remote.MovieResponse
import com.example.moviereview.data.remote.RetrofitService
import com.example.moviereview.remote.enqueueListener
import retrofit2.Call

class SearchViewModel : ViewModel() {
    var editTextSearch = MutableLiveData<String>()

    private val _movieList = MutableLiveData<List<MovieResponse.Item>>()
    val movieList : LiveData<List<MovieResponse.Item>> = _movieList

    var list = listOf<MovieResponse.Item>()

    fun getMovieList() {
        val call: Call<MovieResponse> = RetrofitService.getInstance()
            .getMovieList("CqffDKypVaajB6acrJLK", "LJ0prk1zCh", editTextSearch.value!!)

        call.enqueueListener(
            onSuccess = {
                list = it.body()!!.items
                _movieList.value = list
            },
            onError = {
                Log.e("RETROFIT_ERROR", it.errorBody().toString())
            }
        )
    }
}