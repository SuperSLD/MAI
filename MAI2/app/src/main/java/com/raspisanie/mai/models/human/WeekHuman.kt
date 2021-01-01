package com.raspisanie.mai.models.human

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeekHuman(
        var number: Int,
        var date: String,
        val days: MutableList<DayHuman>
): Parcelable

@Parcelize
data class DayHuman(
        var date: String,
        var subjects: MutableList<Subject>
): Parcelable

@Parcelize
data class Subject(
        var name: String,
        var location: String,
        var teacher: String,
        var type: String,
        var time: String
): Parcelable

