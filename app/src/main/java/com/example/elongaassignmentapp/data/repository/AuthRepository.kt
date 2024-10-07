package com.example.elongaassignmentapp.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

interface AuthRepository {
    suspend fun isAuthorized(): Boolean
    suspend fun setAuthorized(newValue: Boolean)
}

// Create a DataStore instance
val Context.dataStore by preferencesDataStore(name = "user_prefs")

class AuthRepositoryImpl(private val context: Context) : AuthRepository {

    // Key for the authorization flag
    private val AUTHORIZED_KEY = booleanPreferencesKey("authorized_key")

    // Get authorization status
    override suspend fun isAuthorized(): Boolean = try {
        context.dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences())
                else throw exception
            }
            .map { preferences -> preferences[AUTHORIZED_KEY] == true }
            .first() // Fetch the first result and stop the flow
    } catch (e: Exception) {
        false // In case of any exception, return false
    }

    // Save authorization status
    override suspend fun setAuthorized(newValue: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[AUTHORIZED_KEY] = newValue
        }
    }
}
