package org.tubeskelompok1.rajutmobile.di

import io.ktor.client.HttpClient
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.koin.core.context.startKoin
import org.tubeskelompok1.rajutmobile.data.AppRepository
import org.tubeskelompok1.rajutmobile.data.local.AppPreferences
import org.tubeskelompok1.rajutmobile.data.remote.ArajutApi
import org.tubeskelompok1.rajutmobile.data.remote.HttpClientFactory

expect val platformModule: Module

val sharedModule = module {
    single { AppPreferences(get()) }
    single<HttpClient> { HttpClientFactory.create(get(), get()) }
    single { ArajutApi(get()) }
    single { AppRepository(get(), get()) }
}

fun initKoin(config: KoinAppDeclaration? = null): KoinApplication = startKoin {
    config?.invoke(this)
    modules(sharedModule, platformModule)
}
