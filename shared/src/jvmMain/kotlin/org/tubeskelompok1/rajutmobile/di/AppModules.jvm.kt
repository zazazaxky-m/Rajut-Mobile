package org.tubeskelompok1.rajutmobile.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import org.koin.dsl.module
import org.tubeskelompok1.rajutmobile.data.local.DATA_STORE_FILE_NAME
import org.tubeskelompok1.rajutmobile.data.local.createDataStore
import java.io.File

actual val platformModule = module {
    single<HttpClientEngine> { CIO.create() }
    single<DataStore<Preferences>> {
        val directory = File(System.getProperty("user.home"), ".arajut").apply { mkdirs() }
        createDataStore(File(directory, DATA_STORE_FILE_NAME).absolutePath)
    }
}
