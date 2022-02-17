package no.shortcut.androidtemplate.common.service

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "prefs")

class DataStorePreferences(
    context: Context,
) : PreferenceService {

    private val dataStore: DataStore<Preferences> by lazy {
        context.dataStore
    }

    private val onboardingCompletedKey = booleanPreferencesKey("APP_ONBOARDED")
    override val onboardingCompleted: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[onboardingCompletedKey] ?: false
    }

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        dataStore.edit { store ->
            store[onboardingCompletedKey] = completed
        }
    }
}
