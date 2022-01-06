package com.example.moviereview

import android.app.AlertDialog
import android.app.Application
import android.app.Dialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
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
            reviewRepository.insertList(
                ReviewModel(
                    null,
                    list,
                    binding.ratingDialog.rating,
                    binding.editTextDialogReview.text.toString()
                )
            )

            hideKeyboard()
            dismiss()
        }
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.editTextDialogReview.windowToken, 0)
    }
}