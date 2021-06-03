package com.raspisanie.mai.models.local

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StudentOrganizationLocal(
        var name: String,
        var address: String,
        var contact: String
): Parcelable