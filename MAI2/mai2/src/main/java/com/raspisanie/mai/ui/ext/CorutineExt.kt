package com.raspisanie.mai.ui.ext

import kotlinx.coroutines.CoroutineExceptionHandler

fun createHandler(block: (Throwable)->Unit) = CoroutineExceptionHandler { _, throwable ->
    block(throwable)
}

fun createEmptyHandler() = CoroutineExceptionHandler { _, _ ->  }