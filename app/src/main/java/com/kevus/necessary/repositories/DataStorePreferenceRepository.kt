package com.kevus.necessary.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStorePreferenceRepository(context: Context) {
    private val dataStore: DataStore<Preferences> = context.createDataStore(name = "SampleData")

    private val isBirthdayScreenActiveDefault = false
    private val defaultActiveTheme = "Dark"

    companion object {
        val PREF_BIRTHDAYSCREEN = preferencesKey<Boolean>("birthday_tab")
        val PREF_ACTIVETHEME = preferencesKey<String>("active_theme")

        private var INSTANCE: DataStorePreferenceRepository? = null

        fun getInstance(context: Context): DataStorePreferenceRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }
                val instance = DataStorePreferenceRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }

    //setValue
    suspend fun setBirthdayScreenTab(isActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[PREF_BIRTHDAYSCREEN] = isActive
        }
    }

    //getValue
    val getBirthdayScreenTabActive: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[PREF_BIRTHDAYSCREEN] ?: isBirthdayScreenActiveDefault
        }

    //setValue
    suspend fun setActiveTheme(themeName: String) {
        dataStore.edit { preferences ->
            preferences[PREF_ACTIVETHEME] = themeName
        }
    }

    //getValue
    val getActiveThemeName: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[PREF_ACTIVETHEME] ?: defaultActiveTheme
        }
}
