package com.raspisanie.mai.data.net.models

data class LibraryResponse(
     val id: String,
     val name: String,
     val sections: MutableList<LibrarySectionResponse>
)

data class LibrarySectionResponse(
     val id: String,
     val name: String,
     val location: String
)