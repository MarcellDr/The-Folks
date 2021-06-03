package com.marcelldr.thefolks.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DashboardButton(
    val imageDrawable: Int,
    val title: String,
    val logoUrl: String? = null,
    val description: String? = null,
    val linkUrl: String? = null
) : Parcelable
