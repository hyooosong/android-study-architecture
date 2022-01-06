package com.example.moviereview

import android.app.AlertDialog
import android.app.Application
import android.app.Dialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import com.example.moviereview.databinding.DialogReviewBinding
import com.example.moviereview.network.MovieResponse

class ReviewDialog(
    private val list: MovieResponse.Item,
    private val title: String,
    application: Application
) : DialogFragment() {
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
        binding.textviewDialogMovie.text = title
    }

    private fun clickNegative() {
        binding.btnDialogNegative.setOnClickListener {
            dismiss()
        }
    }

    private fun clickPositive() {
        binding.btnDialogPositive.setOnClickListener {
            if (hasReview(list)) {
                reviewRepository.updateList(list, binding.editTextDialogReview.text.toString())
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

            hideKeyboard()
            dismiss()
            Toast.makeText(requireContext(), "리뷰가 등록되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setReview() {
        if(hasReview(list)) {
            binding.ratingDialog.rating = reviewRepository.getItems(list)!!.rating
            binding.editTextDialogReview.setText(reviewRepository.getItems(list)!!.review)
        }
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager =
            context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.editTextDialogReview.windowToken, 0)
    }

    private fun hasReview(items: MovieResponse.Item) = reviewRepository.hasEntity(items) > 0
}