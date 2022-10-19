package com.raspisanie.mai.common

import com.raspisanie.mai.common.extesions.firstItems
import com.raspisanie.mai.common.extesions.toList
import io.mockk.junit5.MockKExtension
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class CommonExtensionsTest {

    @Test
    fun `get first N items from list if size smaller than N`() {
        val list = listOf(0, 1, 2, 3, 4)
        assert(list.firstItems(3) == listOf(0, 1, 2))
    }

    @Test
    fun `get first N items from list if size bigger than N`() {
        val list = listOf(0, 1, 2, 3, 4)
        assert(list.firstItems(8) == listOf(0, 1, 2, 3, 4))
    }

    @Test
    fun `create list from single object`() {
        val list = 9.toList(10)
        assert(list.size == 10)
    }

    @Test
    fun `create empty list from single object`() {
        val list = 9.toList(0)
        assert(list.size == 0)
    }
}