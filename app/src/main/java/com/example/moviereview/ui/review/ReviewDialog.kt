package com.example.moviereview.ui.review

import android.app.AlertDialog
import android.app.Application
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.moviereview.databinding.DialogReviewBinding
import com.example.moviereview.network.MovieResponse
import com.example.moviereview.presenter.ReviewContract
import com.example.moviereview.room.ReviewRepository
import com.example.moviereview.utils.hideKeyboard
import com.example.moviereview.utils.removeHtmlTag

class ReviewDialog(private val list: MovieResponse.Item, private val application: Application) :
    DialogFragment(), ReviewContract.ReviewView {
    private lateinit var binding: DialogReviewBinding
    private lateinit var reviewPresenter: ReviewPresenter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogReviewBinding.inflate(requireActivity().layoutInflater)
        initPresenter()
        reviewPresenter.takeView(this)

        binding.presenter = reviewPresenter

        setTitle()
        clickNegative()
        clickPositive()
        reviewPresenter.setReviewContent(list)

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException()
    }

    private fun initPresenter() {
        reviewPresenter = ReviewPresenter(ReviewRepository(application))
    }

    private fun setTitle() {
        binding.textviewDialogMovie.text = list.title.removeHtmlTag().toString()
    }

    private fun clickNegative() {
        binding.btnDialogNegative.setOnClickListener {
            dismiss()
        }
    }

    private fun clickPositive() {
        binding.btnDialogPositive.setOnClickListener {
            reviewPresenter.updateReviewModel(list, binding.ratingDialog.rating)
            requireContext().hideKeyboard(binding.editTextDialogReview)

            dismiss()
            Toast.makeText(requireContext(), "리뷰가 등록되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        reviewPresenter.dropView()
    }
}