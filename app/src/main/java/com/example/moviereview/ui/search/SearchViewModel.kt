package com.example.moviereview.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviereview.data.remote.MovieDataSource
import com.example.moviereview.data.remote.MovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val dataSource: MovieDataSource) : ViewModel() {

    sealed class EventMovie {
        data class Empty(val editText: String?) : EventMovie()
        data class SetMovie(val list: List<MovieResponse.Item>): EventMovie()
    }

    var editTextSearch = MutableLiveData<String>()

    private val _eventData = MutableSharedFlow<EventMovie>()
    val eventData = _eventData.asSharedFlow()

    fun getMovieData() {
        viewModelScope.launch(Dispatchers.IO) {
            editTextSearch.value?.let {
                val list = dataSource.getMovieList("CqffDKypVaajB6acrJLK", "LJ0prk1zCh", it)
                emitEvent(EventMovie.SetMovie(list.items))
            } ?: run {
                emitEvent(EventMovie.Empty("검색어를 입력하세요"))
            }
        }
    }

    private fun emitEvent(event: EventMovie) {
        viewModelScope.launch {
            _eventData.emit(event)
        }
    }
}