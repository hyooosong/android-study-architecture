package com.example.moviereview.ui.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviereview.databinding.FragmentReviewBinding
import com.example.moviereview.presenter.ReviewContract
import com.example.moviereview.room.ReviewModel
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()
        initRecyclerView()
    }

    private fun initPresenter() {
        reviewPresenter = ReviewPresenter(ReviewRepository(requireActivity().application))
        reviewPresenter.takeView(this)
    }

    private fun initRecyclerView() {
        adapter = ReviewAdapter()
        binding.rcvReview.adapter = adapter
    }

    override fun listToAdapter(list: List<ReviewModel>?) {
        adapter.submitList(list)
    }

    override fun onResume() {
        super.onResume()
        reviewPresenter.setReviewList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        reviewPresenter.dropView()
    }
}