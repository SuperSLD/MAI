package com.raspisanie.mai.models.server

data class SportResponse(
     val id: String,
     val name: String,
     val address: String,
     val sections: MutableList<SportSectionResponse>
)

data class SportSectionResponse(
     val id: String,
     val name: String,
     val contactName: String,
     val contact: String
)