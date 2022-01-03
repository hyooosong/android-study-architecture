package com.example.moviereview

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.example.moviereview.databinding.FragmentSearchBinding
import com.example.moviereview.network.MovieResponse
import com.example.moviereview.network.RetrofitService
import com.example.moviereview.network.enqueueListener
import retrofit2.Call

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchAdapter
    var searchMovie = MutableLiveData<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.fragment = this
        initRecyclerView()
        callMovieList()
        return binding.root
    }

    private fun initRecyclerView() {
        adapter = SearchAdapter(requireContext())
        binding.rcvSearch.adapter = adapter
    }

    private fun callMovieList() {
        binding.btnSearch.setOnClickListener {
            if (searchMovie.value.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "검색어를 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val call: Call<MovieResponse> =
                RetrofitService.getInstance().getMovieList(
                    "CqffDKypVaajB6acrJLK",
                    "LJ0prk1zCh",
                    searchMovie.value!!
                )

            call.enqueueListener(
                onSuccess = {
                    adapter.submitList(it.body()!!.items)
                },
                onError = {
                    Toast.makeText(requireContext(), "다시 시도해주세요", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}