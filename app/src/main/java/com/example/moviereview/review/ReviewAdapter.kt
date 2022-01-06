package com.example.moviereview.review

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviereview.room.ReviewModel
import com.example.moviereview.databinding.ItemReviewListBinding

class ReviewAdapter :
    ListAdapter<ReviewModel, ReviewAdapter.ReviewViewHolder>(ReviewDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder =
        ReviewViewHolder(
            ItemReviewListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ReviewViewHolder(private val binding: ItemReviewListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: ReviewModel) {
            Glide.with(binding.imgReview.context)
                .load(data.items!!.image)
                .into(binding.imgReview)
            binding.ratingReview.rating = data.rating
            binding.textViewReview.text = "\"${data.review}\""
            binding.textViewReviewTitle.text = removeHtmlTag(data.items.title).toString()
        }

        private fun removeHtmlTag(html: String) =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(html, 0).toString()
            } else {
                Log.e("ErrorVersion", "")
            }
    }

    private class ReviewDiffUtil : DiffUtil.ItemCallback<ReviewModel>() {
        override fun areItemsTheSame(oldItem: ReviewModel, newItem: ReviewModel) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: ReviewModel, newItem: ReviewModel) =
            oldItem == newItem
    }

}