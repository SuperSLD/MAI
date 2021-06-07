package com.raspisanie.mai.models.server

import android.telephony.cdma.CdmaCellLocation

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