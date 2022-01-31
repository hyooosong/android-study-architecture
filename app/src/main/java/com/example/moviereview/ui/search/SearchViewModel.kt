package com.example.moviereview.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviereview.data.remote.MovieDataSource
import com.example.moviereview.data.remote.MovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val dataSource: MovieDataSource) : ViewModel() {

    enum class Event {
        NOT_INPUT
    }

    var editTextSearch = MutableLiveData<String>()

    private val _movieList = MutableLiveData<List<MovieResponse.Item>>()
    val movieList: LiveData<List<MovieResponse.Item>> = _movieList

    private val _event = MutableLiveData<Event>()
    val event : LiveData<Event> = _event

    fun getMovieList() {
        viewModelScope.launch(Dispatchers.IO) {
            editTextSearch.value?.let {
                val list = dataSource.getMovieList(
                    "CqffDKypVaajB6acrJLK",
                    "LJ0prk1zCh",
                    editTextSearch.value ?: " "
                )
                withContext(Dispatchers.Main) {
                    _movieList.value = list.items
                }
            } ?: run {
                withContext(Dispatchers.Main) {
                    _event.value = Event.NOT_INPUT
                }
            }
        }
    }
}