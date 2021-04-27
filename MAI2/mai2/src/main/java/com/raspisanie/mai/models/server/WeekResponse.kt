package com.raspisanie.mai.models.server

import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeekResponse(
        var number: Int,
        var date: String,
        val days: MutableList<DayResponse>
): Parcelable

@Parcelize
data class DayResponse(
        var id: String,
        var date: String,
        var subjects: MutableList<SubjectResponse>
): Parcelable

@Parcelize
data class SubjectResponse(
        var id: String,
        var name: String,
        var room: RoomResponse,
        var lector: TeacherResponse,
        var type: String,
        var timeStart: String,
        var timeEnd: String,
        var link: String,
        var number: String
): Parcelable

@Parcelize
data class RoomResponse(
        var id: String,
        var name: String
): Parcelable

@Parcelize
data class TeacherResponse(
        var id: String,
        var name: String
): Parcelable

