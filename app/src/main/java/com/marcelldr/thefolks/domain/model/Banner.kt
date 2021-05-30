package com.marcelldr.thefolks.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Banner(
    val imageUrl: String,
    val linkUrl: String
) : Parcelable
