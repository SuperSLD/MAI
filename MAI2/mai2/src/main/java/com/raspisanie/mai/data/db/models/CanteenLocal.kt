package com.raspisanie.mai.data.db.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CanteenLocal(
        var name: String,
        var location: String,
        var time: String
): Parcelable