package com.example.moviereview.ui.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.moviereview.databinding.FragmentReviewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ReviewFragment : Fragment() {
    private lateinit var binding: FragmentReviewBinding
    private val reviewAdapter by lazy { ReviewAdapter() }
    private val reviewViewModel by viewModels<ReviewViewModel>()

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
        lifecycleScope.launch {
            val list = reviewViewModel.getReviewList()
            withContext(Dispatchers.Main) {
                reviewAdapter.submitList(list)
            }
        }
    }
}