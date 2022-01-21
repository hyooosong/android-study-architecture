package com.example.moviereview.ui.review

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.moviereview.databinding.DialogReviewBinding
import com.example.moviereview.network.MovieResponse
import com.example.moviereview.presenter.ReviewDialogContract
import com.example.moviereview.room.ReviewRepository
import com.example.moviereview.utils.hideKeyboard
import com.example.moviereview.utils.removeHtmlTag

class ReviewDialog(private val list: MovieResponse.Item, private val application: Application) :
    DialogFragment(), ReviewDialogContract.ReviewDialogView {
    private lateinit var binding: DialogReviewBinding
    private lateinit var reviewDialogPresenter: ReviewDialogPresenter

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
        initPresenter()
        binding.presenter = reviewDialogPresenter
        setTitle()
        clickNegative()
        clickPositive()
        reviewDialogPresenter.setReviewContent(list)
    }

    private fun initPresenter() {
        reviewDialogPresenter = ReviewDialogPresenter(ReviewRepository(application))
        reviewDialogPresenter.takeView(this)
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
            reviewDialogPresenter.updateReviewModel(list, binding.ratingDialog.rating)
            requireContext().hideKeyboard(binding.editTextDialogReview)
            dismiss()
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        reviewDialogPresenter.dropView()
    }
}