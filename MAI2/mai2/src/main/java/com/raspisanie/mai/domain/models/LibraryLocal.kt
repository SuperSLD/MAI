package com.raspisanie.mai.domain.models

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