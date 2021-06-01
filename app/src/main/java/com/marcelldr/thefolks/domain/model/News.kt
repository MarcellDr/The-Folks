package com.marcelldr.thefolks.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val source: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String
) : Parcelable