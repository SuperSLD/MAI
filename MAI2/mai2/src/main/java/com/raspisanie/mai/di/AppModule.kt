package com.raspisanie.mai.di

import org.koin.dsl.module

fun appModule() = module {
    provideDataFlow()
    provideUeeCases()
    provideControllers()
    provideNetModules()
}