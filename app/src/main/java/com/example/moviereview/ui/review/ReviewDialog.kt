package com.example.moviereview.ui.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.moviereview.data.remote.MovieResponse
import com.example.moviereview.databinding.DialogReviewBinding
import com.example.moviereview.ext.showToast
import com.example.moviereview.utils.hideKeyboard
import com.example.moviereview.utils.removeHtmlTag
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewDialog(private val list: MovieResponse.Item) :
    DialogFragment() {
    private lateinit var binding: DialogReviewBinding
    private val reviewViewModel by viewModels<ReviewViewModel>()

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
            requireContext().showToast("리뷰가 등록되었습니다")
        }
    }

    private fun setReview() {
        reviewViewModel.setExistingContent(list)
    }
}