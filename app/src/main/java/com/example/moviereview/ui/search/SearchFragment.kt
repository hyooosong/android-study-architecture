package com.example.moviereview.ui.search

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.moviereview.R
import com.example.moviereview.databinding.FragmentSearchBinding
import com.example.moviereview.presenter.SearchContract
import com.example.moviereview.ui.review.ReviewDialog
import com.example.moviereview.utils.hideKeyboard

class SearchFragment : Fragment(), SearchContract.SearchView {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchAdapter
    private lateinit var searchPresenter: SearchPresenter
    var searchMovie = MutableLiveData<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        initPresenter()
        searchPresenter.takeView(this)
        binding.presenter = searchPresenter

        initRecyclerView()
        callMovieList()
        return binding.root
    }

    private fun initPresenter() {
        searchPresenter = SearchPresenter()
    }

    private fun initRecyclerView() {
        adapter = SearchAdapter({
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.link))
            startActivity(intent)
        }, { list ->
            val dialog = ReviewDialog(list, requireActivity().application)
            dialog.show(childFragmentManager, "REVIEW_DIALOG")
        })
        binding.rcvSearch.adapter = adapter
    }

    private fun callMovieList() {
        binding.btnSearch.setOnClickListener {
            searchPresenter.callMovieList { listToAdapter() }
            requireContext().hideKeyboard(binding.editTextSearch)

            if(searchPresenter.isNullEditText) {
                Toast.makeText(
                    requireContext(), getString(R.string.search_enter), Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
        }
    }

    private fun listToAdapter() {
        adapter.submitList(searchPresenter.list)
    }

    override fun onDestroy() {
        super.onDestroy()
        searchPresenter.dropView()
    }
}