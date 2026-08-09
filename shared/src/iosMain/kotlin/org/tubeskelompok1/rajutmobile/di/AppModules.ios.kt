package org.tubeskelompok1.rajutmobile.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.dsl.module
import org.tubeskelompok1.rajutmobile.data.local.DATA_STORE_FILE_NAME
import org.tubeskelompok1.rajutmobile.data.local.createDataStore
import platform.Foundation.NSApplicationSupportDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual val platformModule = module {
    single<HttpClientEngine> { Darwin.create() }
    single<DataStore<Preferences>> {
        val directory = NSFileManager.defaultManager.URLForDirectory(
            NSApplicationSupportDirectory,
            NSUserDomainMask,
            appropriateForURL = null,
            create = true,
            error = null
        )?.path ?: error("Application Support directory not found")
        createDataStore("$directory/$DATA_STORE_FILE_NAME")
    }
}
