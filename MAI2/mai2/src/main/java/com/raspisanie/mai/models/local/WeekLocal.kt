package com.raspisanie.mai.models.local

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeekLocal(
        var number: Int,
        var date: String,
        val days: MutableList<DayLocal>
): Parcelable

@Parcelize
data class DayLocal(
        var date: String,
        var subjects: MutableList<SubjectLocal>
): Parcelable

@Parcelize
data class SubjectLocal(
        var name: String,
        var location: String,
        var teacher: String,
        var type: String,
        var time: String
): Parcelable

