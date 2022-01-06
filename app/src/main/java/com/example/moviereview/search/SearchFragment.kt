package com.example.moviereview.search

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.moviereview.R
import com.example.moviereview.review.ReviewDialog
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
        adapter = SearchAdapter({
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.link))
            startActivity(intent)
        }, { list, title ->
            val dialog = ReviewDialog(list, title, requireActivity().application)
            dialog.show(childFragmentManager, "REVIEW_DIALOG")
        })
        binding.rcvSearch.adapter = adapter
    }

    private fun callMovieList() {
        binding.btnSearch.setOnClickListener {
            if (searchMovie.value.isNullOrEmpty()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.search_enter),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val call: Call<MovieResponse> =
                RetrofitService.getInstance().getMovieList(
                    getString(R.string.naver_client_id),
                    getString(R.string.naver_client_secret),
                    searchMovie.value!!
                )

            call.enqueueListener(
                onSuccess = {
                    adapter.submitList(it.body()!!.items)
                },
                onError = {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.try_again),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }
}