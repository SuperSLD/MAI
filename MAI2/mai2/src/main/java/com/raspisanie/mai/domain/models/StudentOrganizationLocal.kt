package com.raspisanie.mai.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StudentOrganizationLocal(
        var name: String,
        var address: String,
        var contact: String
): Parcelable