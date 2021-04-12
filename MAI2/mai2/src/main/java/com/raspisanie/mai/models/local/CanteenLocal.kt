package com.raspisanie.mai.models.local

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CanteenLocal(
        var name: String,
        var location: String,
        var time: String
): Parcelable