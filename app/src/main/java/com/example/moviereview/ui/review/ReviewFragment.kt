package com.example.moviereview.ui.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviereview.data.local.ReviewRepository
import com.example.moviereview.databinding.FragmentReviewBinding

class ReviewFragment : Fragment() {
    private lateinit var binding: FragmentReviewBinding
    private val reviewRepository by lazy { ReviewRepository(requireActivity().application) }
    private val reviewAdapter by lazy { ReviewAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        setReviewList()
    }

    private fun initRecyclerView() {
        binding.rcvReview.adapter = reviewAdapter
    }

    private fun setReviewList() {
        reviewAdapter.submitList(reviewRepository.getList())
    }
}