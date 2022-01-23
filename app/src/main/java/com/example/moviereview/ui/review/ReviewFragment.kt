package com.example.moviereview.ui.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviereview.databinding.FragmentReviewBinding
import com.example.moviereview.data.local.ReviewRepository

class ReviewFragment : Fragment() {
    private lateinit var binding: FragmentReviewBinding
    private lateinit var reviewRepository: ReviewRepository
    private lateinit var adapter: ReviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewBinding.inflate(inflater, container, false)
        initRecyclerView()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setReviewList()
    }

    private fun initRecyclerView() {
        adapter = ReviewAdapter()
        binding.rcvReview.adapter = adapter
    }

    private fun setReviewList() {
        reviewRepository = ReviewRepository(requireActivity().application)
        adapter.submitList(reviewRepository.getList())
    }
}