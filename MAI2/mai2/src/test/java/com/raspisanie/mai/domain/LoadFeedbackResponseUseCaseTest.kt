package com.raspisanie.mai.domain

import com.raspisanie.mai.data.net.DataWrapper
import com.raspisanie.mai.data.net.retrofit.ApiService
import com.raspisanie.mai.domain.usecases.main.LoadFeedbackResponseUseCase
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.security.InvalidParameterException

@ExtendWith(MockKExtension::class)
class LoadFeedbackResponseUseCaseTest {

    @MockK
    private lateinit var service: ApiService

    private val loadFeedbackResponseUseCase by lazy {
        LoadFeedbackResponseUseCase(service)
    }

    @Before
    fun init() {
        service = mockk()
    }

    @Test
    fun `success load feedback response`() {
        coEvery { service.getFeedbackResponse() } returns DataWrapper(
            success = true,
            message = "",
            data = listOf()
        )

        runBlocking { loadFeedbackResponseUseCase() }
    }

    @Test
    fun `error on load feedback response`() {
        coEvery { service.getFeedbackResponse() } returns DataWrapper(
            success = false,
            message = "",
            data = listOf()
        )

        assertThrows<InvalidParameterException> {
            runBlocking { loadFeedbackResponseUseCase() }
        }
    }
}