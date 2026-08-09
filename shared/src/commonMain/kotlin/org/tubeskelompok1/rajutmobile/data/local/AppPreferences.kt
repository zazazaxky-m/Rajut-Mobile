package org.tubeskelompok1.rajutmobile.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AppPreferences(private val dataStore: DataStore<Preferences>) {
    private val tokenKey = stringPreferencesKey("AUTH_TOKEN")
    private val onboardingCompletedKey = booleanPreferencesKey("ONBOARDING_COMPLETED")

    val token = dataStore.data.map { it[tokenKey].orEmpty() }

    suspend fun saveToken(value: String) {
        dataStore.edit { it[tokenKey] = value }
    }

    suspend fun getToken(): String = token.first()

    suspend fun hasCompletedOnboarding(): Boolean = dataStore.data
        .map { it[onboardingCompletedKey] ?: false }
        .first()

    suspend fun completeOnboarding() {
        dataStore.edit { it[onboardingCompletedKey] = true }
    }

    suspend fun clear() {
        dataStore.edit { it.remove(tokenKey) }
    }

    suspend fun clearForLogout() {
        dataStore.edit {
            it.remove(tokenKey)
            it.remove(onboardingCompletedKey)
        }
    }
}
