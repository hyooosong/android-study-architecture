package com.example.moviereview

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviereview.databinding.FragmentReviewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import kotlin.coroutines.CoroutineContext

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
        Thread(Runnable {
            reviewRepository = ReviewRepository(requireActivity().application)
            val list = reviewRepository.getList()
            requireActivity().runOnUiThread {
                adapter.submitList(list)
            }
        }).start()
    }
}