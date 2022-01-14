package com.example.moviereview.ui.review

import android.app.AlertDialog
import android.app.Application
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.moviereview.databinding.DialogReviewBinding
import com.example.moviereview.network.MovieResponse
import com.example.moviereview.room.ReviewModel
import com.example.moviereview.room.ReviewRepository
import com.example.moviereview.utils.hideKeyboard
import com.example.moviereview.utils.removeHtmlTag

class ReviewDialog(private val list: MovieResponse.Item, application: Application) :
    DialogFragment() {
    private lateinit var binding: DialogReviewBinding
    private var reviewRepository = ReviewRepository(application)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogReviewBinding.inflate(requireActivity().layoutInflater)
        setTitle()
        clickNegative()
        clickPositive()
        setReview()
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException()
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
            if (hasReview(list)) {
                reviewRepository.updateReview(list, binding.editTextDialogReview.text.toString())
                reviewRepository.updateRating(list, binding.ratingDialog.rating)
            } else {
                reviewRepository.insertList(
                    ReviewModel(
                        null,
                        list,
                        binding.ratingDialog.rating,
                        binding.editTextDialogReview.text.toString()
                    )
                )
            }
            requireContext().hideKeyboard(binding.editTextDialogReview)

            dismiss()
            Toast.makeText(requireContext(), "리뷰가 등록되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setReview() {
        if (hasReview(list)) {
            val list = reviewRepository.getItems(list)!!
            binding.ratingDialog.rating = list.rating
            binding.editTextDialogReview.setText(list.review)
        }
    }

    private fun hasReview(items: MovieResponse.Item) = reviewRepository.hasEntity(items) > 0
}