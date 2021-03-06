package com.raspisanie.mai

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.raspisanie.mai.extesions.getUUID
import com.raspisanie.mai.extesions.mappers.toLocal
import com.raspisanie.mai.extesions.mappers.toRealm
import com.raspisanie.mai.models.server.*
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import online.jutter.supersld.JTStateMachine
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class WeekTest {
    @Test
    fun goToState() {
        val weekResponse = WeekResponse(
                number = 1,
                date = "09.03.2021 - 16.03.2021",
                days = mutableListOf(
                        DayResponse(
                                id = getUUID(),
                                date = "09.03.2021",
                                subjects = mutableListOf(
                                        SubjectResponse(
                                                id = getUUID(),
                                                name = "Сопромат",
                                                link = "",
                                                lector = TeacherResponse(
                                                        id = getUUID(),
                                                        name = "Малинина"
                                                ),
                                                room = RoomResponse(
                                                        id = getUUID(),
                                                        name = "ГУК 228 Б"
                                                ),
                                                timeEnd = "10:30",
                                                timeStart = "09:00",
                                                number = "1",
                                                type = "ПЗ"
                                        ),
                                        SubjectResponse(
                                                id = getUUID(),
                                                name = "Сопромат",
                                                link = "",
                                                lector = TeacherResponse(
                                                        id = getUUID(),
                                                        name = "Малинина"
                                                ),
                                                room = RoomResponse(
                                                        id = getUUID(),
                                                        name = "ГУК 228 Б"
                                                ),
                                                timeEnd = "10:45",
                                                timeStart = "12:15",
                                                number = "1",
                                                type = "ПЗ"
                                        )
                                )
                        ),
                        DayResponse(
                                id = getUUID(),
                                date = "10.03.2021",
                                subjects = mutableListOf(
                                        SubjectResponse(
                                                id = getUUID(),
                                                name = "Сопромат",
                                                link = "",
                                                lector = TeacherResponse(
                                                        id = getUUID(),
                                                        name = "Малинина"
                                                ),
                                                room = RoomResponse(
                                                        id = getUUID(),
                                                        name = "ГУК 228 Б"
                                                ),
                                                timeEnd = "10:30",
                                                timeStart = "09:00",
                                                number = "1",
                                                type = "ПЗ"
                                        ),
                                        SubjectResponse(
                                                id = getUUID(),
                                                name = "Сопромат",
                                                link = "",
                                                lector = TeacherResponse(
                                                        id = getUUID(),
                                                        name = "Малинина"
                                                ),
                                                room = RoomResponse(
                                                        id = getUUID(),
                                                        name = "ГУК 228 Б"
                                                ),
                                                timeEnd = "10:45",
                                                timeStart = "12:15",
                                                number = "1",
                                                type = "ПЗ"
                                        )
                                )
                        )
                )
        )

        val weekRealm = weekResponse.toRealm()
        val weekLocal = weekRealm.toLocal()

        //assertEquals(0, stateMachine.getCurrentState())
    }
}