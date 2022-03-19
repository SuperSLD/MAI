package com.raspisanie.mai.data.db.models

import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
open class GroupRealm(
        @PrimaryKey
        var id: String? = null,
        var name: String? = null,
        var fac: String? = null,
        var level: String? = null,
        var course: Int? = null,
        var selected: Boolean = false
) : RealmObject(), Parcelable