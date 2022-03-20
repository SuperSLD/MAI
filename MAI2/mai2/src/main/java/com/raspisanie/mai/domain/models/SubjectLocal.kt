package com.raspisanie.mai.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DayLocal(
        var id: String,
        var date: String,
        var subjects: MutableList<SubjectLocal>
): Parcelable

@Parcelize
data class SubjectLocal(
    var id: String,
    var name: String,
    var room: RoomLocal,
    var teacher: TeacherLocal,
    var type: String,
    var timeStart: String,
    var timeEnd: String,
    var link: String,
    var number: String,
    var date: String
): Parcelable {
    companion object {
        const val PZ = "ПЗ"
        const val LK = "ЛК"
        const val EXAM = "Экзамен"
    }
}

@Parcelize
data class RoomLocal(
        var id: String,
        var name: String
): Parcelable

@Parcelize
data class TeacherLocal(
        var id: String,
        var name: String
): Parcelable


