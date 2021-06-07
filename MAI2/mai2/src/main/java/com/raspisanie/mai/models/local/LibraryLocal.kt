package com.raspisanie.mai.models.local

data class LibraryLocal(
     val id: String,
     val name: String,
     val sections: MutableList<LibrarySectionLocal>
)

data class LibrarySectionLocal(
     val id: String,
     val name: String,
     val location: String
)