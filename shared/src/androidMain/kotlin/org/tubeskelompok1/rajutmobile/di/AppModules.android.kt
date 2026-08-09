package org.tubeskelompok1.rajutmobile.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.tubeskelompok1.rajutmobile.data.local.DATA_STORE_FILE_NAME
import org.tubeskelompok1.rajutmobile.data.local.createDataStore

actual val platformModule = module {
    single<HttpClientEngine> { OkHttp.create() }
    single<DataStore<Preferences>> {
        createDataStore(androidContext().filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath)
    }
}
