package com.example.moviereview.ui.review

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviereview.data.local.ReviewRepository
import com.example.moviereview.data.remote.MovieResponse
import com.example.moviereview.databinding.DialogReviewBinding
import com.example.moviereview.utils.hideKeyboard
import com.example.moviereview.utils.removeHtmlTag

class ReviewDialog(private val list: MovieResponse.Item, application: Application) :
    DialogFragment() {
    private lateinit var binding: DialogReviewBinding
    private val reviewViewModel by viewModels<ReviewViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ReviewViewModel(ReviewRepository(application)) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogReviewBinding.inflate(requireActivity().layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = this@ReviewDialog
            viewModel = reviewViewModel
        }
        setTitle()
        setReview()
        clickNegative()
        clickPositive()
    }

    private fun setTitle() {
        binding.textviewDialogMovie.text = list.title.removeHtmlTag().toString()
    }

    private fun clickNegative() {
        binding.btnDialogNegative.setOnClickListener {
            requireContext().hideKeyboard(binding.editTextDialogReview)
            dismiss()
        }
    }

    private fun clickPositive() {
        binding.btnDialogPositive.setOnClickListener {
            reviewViewModel.updateReview(list, binding.ratingDialog.rating)
            requireContext().hideKeyboard(binding.editTextDialogReview)
            dismiss()
            Toast.makeText(requireContext(), "리뷰가 등록되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setReview() {
        reviewViewModel.setExistingContent(list)
    }
}