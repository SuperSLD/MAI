package com.raspisanie.mai.models.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class NewsRealm(
    @PrimaryKey
    var id: String? = null,
    var title: String? = null,
    var author: String? = null,
    var text: String? = null,
    var warning: Boolean? = null,
    var createdAt: String? = null
) : RealmObject()