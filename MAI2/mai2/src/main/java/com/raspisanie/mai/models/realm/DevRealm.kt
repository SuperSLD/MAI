package com.raspisanie.mai.models.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class DevRealm(
     @PrimaryKey
     var id: String? = null,
     var name: String? = null,
     var from: String? = null,
     var link: String? = null
) : RealmObject()