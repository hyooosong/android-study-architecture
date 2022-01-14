package com.example.moviereview.utils

import android.os.Build
import android.text.Html
import android.util.Log

fun String.removeHtmlTag() =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, 0).toString()
    } else {
        Log.e("ErrorVersion", "")
    }