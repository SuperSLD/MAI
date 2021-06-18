package com.raspisanie.mai.models.local

data class SportLocal(
     val id: String,
     val name: String,
     val address: String,
     val sections: MutableList<SportSectionLocal>
)

data class SportSectionLocal(
     val id: String,
     val name: String,
     val contactName: String,
     val contact: String
)