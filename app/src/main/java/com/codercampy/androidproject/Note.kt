package com.codercampy.androidproject

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    val title: String,
    val body: String
) : Parcelable