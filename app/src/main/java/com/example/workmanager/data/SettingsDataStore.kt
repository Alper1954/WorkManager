package com.example.workmanager.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "myWorkRequestId"
)

class SettingsDataStore(preference_datastore: DataStore<Preferences>) {

    private val WORK_REQUEST_ID = stringPreferencesKey("workRequestId")

    suspend fun workRequestIdToPreferencesStore(workRequestId: String, context: Context) {
        context.dataStore.edit { preferences ->
            preferences[WORK_REQUEST_ID] = workRequestId
        }
    }

    val workRequestId:Flow<String> = preference_datastore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map{preferences ->
            preferences[WORK_REQUEST_ID] ?: "noValue"
        }
}

