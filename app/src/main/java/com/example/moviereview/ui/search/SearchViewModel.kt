package com.example.moviereview.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviereview.data.remote.MovieDataSource
import com.example.moviereview.data.remote.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(private val dataSource: MovieDataSource) : ViewModel() {
    var editTextSearch = MutableLiveData<String>()

    private val _movieList = MutableLiveData<List<MovieResponse.Item>>()
    val movieList : LiveData<List<MovieResponse.Item>> = _movieList

    fun getMovieList() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = dataSource.getMovieList("CqffDKypVaajB6acrJLK", "LJ0prk1zCh", editTextSearch.value?: " ")
            withContext(Dispatchers.Main) {
                _movieList.value = list.items
            }
        }
    }
}