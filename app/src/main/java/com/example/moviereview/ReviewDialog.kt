package com.example.moviereview

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.example.moviereview.databinding.DialogReviewBinding

class ReviewDialog(private val title : String) : DialogFragment() {
    private lateinit var binding : DialogReviewBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogReviewBinding.inflate(requireActivity().layoutInflater)
        setTitle()
        clickNegative()
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
}