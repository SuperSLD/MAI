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
        var subjects: MutableList<SubjectHuman>
): Parcelable

@Parcelize
data class SubjectHuman(
        var name: String,
        var location: String,
        var teacher: String,
        var type: String,
        var time: String
): Parcelable

