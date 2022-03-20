package com.raspisanie.mai.data.net.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class StudentOrganizationResponse(
        var name: String,
        var address: String,
        var contact: String
)