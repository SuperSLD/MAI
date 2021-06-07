package com.raspisanie.mai.models.server

data class LibraryResponse(
     val id: String,
     val name: String,
     val address: String,
     val sections: MutableList<SportSectionResponse>
)

data class LibrarySectionResponse(
     val id: String,
     val name: String,
     val contactName: String,
     val contact: String
)