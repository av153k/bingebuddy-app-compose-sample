package com.bingebuddy.app.utils

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val Context.appDataStore by preferencesDataStore(name = "app_preferences")

    // Save a key-value pair (String, Int, Boolean, Float, Long)
    suspend fun <T> put(key: Preferences.Key<T>, value: T) {
        context.appDataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    // Retrieve a preference value by key with a default fallback
    fun <T> get(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return context.appDataStore.data.map { preferences ->
            preferences[key] ?: defaultValue
        }
    }

    // Delete a specific key from preferences
    suspend fun <T> delete(key: Preferences.Key<T>) {
        context.appDataStore.edit { preferences ->
            preferences.remove(key)
        }
    }

    // Clear all preferences
    suspend fun clearAll() {
        context.appDataStore.edit { it.clear() }
    }

    // Predefined preference keys (example)
    companion object {
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val EXPLICIT_CONTENT_ALLOWED = booleanPreferencesKey("explicit_content_allowed")
    }
}