package com.example.moviereview.ui.search

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.moviereview.databinding.FragmentSearchBinding
import com.example.moviereview.network.MovieResponse
import com.example.moviereview.presenter.SearchContract
import com.example.moviereview.ui.review.ReviewDialog
import com.example.moviereview.utils.hideKeyboard

class SearchFragment : Fragment(), SearchContract.SearchView {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchAdapter
    private lateinit var searchPresenter: SearchPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()
        binding.presenter = searchPresenter
        initRecyclerView()
        onClickSearch()
    }

    private fun initPresenter() {
        searchPresenter = SearchPresenter()
        searchPresenter.takeView(this)
    }

    private fun initRecyclerView() {
        adapter = SearchAdapter({
            onClickMore(it.link)
        }, { item ->
            showReviewDialog(item, requireActivity().application)
        })
        binding.rcvSearch.adapter = adapter
    }

    private fun onClickSearch() {
        binding.btnSearch.setOnClickListener {
            searchPresenter.callMovieList()
            requireContext().hideKeyboard(binding.editTextSearch)
        }
    }

    override fun onClickMore(link: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(intent)
    }

    override fun showReviewDialog(item: MovieResponse.Item, application: Application) {
        val dialog = ReviewDialog(item, application)
        dialog.show(childFragmentManager, "REVIEW_DIALOG")
    }

    override fun listToAdapter(list: List<MovieResponse.Item>?) {
        adapter.submitList(list)
    }

    override fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        searchPresenter.dropView()
    }
}