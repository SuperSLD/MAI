package com.raspisanie.mai.models.human

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CanteenHuman(
        var name: String,
        var location: String,
        var time: String
): Parcelable