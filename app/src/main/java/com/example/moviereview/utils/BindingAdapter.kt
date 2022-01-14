package com.example.moviereview.utils

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {
    @BindingAdapter("image")
    @JvmStatic
    fun imageBinding(imageView: ImageView, url: String) {
        Glide.with(imageView)
            .load(url)
            .into(imageView)
    }

    @BindingAdapter("removeHtmlTag")
    @JvmStatic
    fun removeHtmlTagBinding(textView: TextView, str: String) {
        textView.text = str.removeHtmlTag().toString()
    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter("review")
    @JvmStatic
    fun reviewBinding(textView: TextView, str: String) {
        textView.text = "\"$str\""
    }
}