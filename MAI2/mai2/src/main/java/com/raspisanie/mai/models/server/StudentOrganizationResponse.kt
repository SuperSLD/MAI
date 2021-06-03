package com.raspisanie.mai.models.server

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class StudentOrganizationResponse(
        var name: String,
        var address: String,
        var contact: String
)