package com.example.moviereview.ui.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviereview.databinding.FragmentReviewBinding
import com.example.moviereview.presenter.ReviewContract
import com.example.moviereview.room.ReviewRepository

class ReviewFragment : Fragment(), ReviewContract.ReviewView {
    private lateinit var binding: FragmentReviewBinding
    private lateinit var adapter: ReviewAdapter
    private lateinit var reviewPresenter: ReviewPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReviewBinding.inflate(inflater, container, false)
        initPresenter()
        reviewPresenter.takeView(this)
        initRecyclerView()

        return binding.root
    }

    private fun initPresenter() {
        reviewPresenter = ReviewPresenter(ReviewRepository(requireActivity().application))
    }

    override fun onResume() {
        super.onResume()
        adapter.submitList(reviewPresenter.setReviewList())
    }

    private fun initRecyclerView() {
        adapter = ReviewAdapter()
        binding.rcvReview.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        reviewPresenter.dropView()
    }
}