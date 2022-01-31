package com.example.moviereview.ext

import android.content.Context
import android.widget.Toast

fun Context.showToast(string: String) {
    Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
}