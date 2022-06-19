package com.kevus.necessary.viemodels

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kevus.necessary.navigation.AppScreens
import com.kevus.necessary.repositories.DataStorePreferenceRepository
import kotlinx.coroutines.launch
//Code from https://github.com/MakeItEasyDev/Jetpack-Compose-Preference-DataStore

class DataStoreViewModel(
    private val dataStorePreferenceRepository: DataStorePreferenceRepository
): ViewModel() {
    private val _birthdayTabActive = MutableLiveData(false)
    val birthdayTabActive: LiveData<Boolean> = _birthdayTabActive

    private val _activeTheme = MutableLiveData("Dark")
    val activeTheme: LiveData<String> = _activeTheme

    private val _startupScreen = MutableLiveData(AppScreens.OverviewScreen.name)
    val startupScreen: LiveData<String> = _startupScreen

    init {
        viewModelScope.launch {
            dataStorePreferenceRepository.getActiveThemeName.collect {
                _activeTheme.value = it
            }
        }
        viewModelScope.launch {
            dataStorePreferenceRepository.getStartupScreen.collect {
                _startupScreen.value = it
            }
        }
        viewModelScope.launch {
            dataStorePreferenceRepository.getBirthdayScreenTabActive.collect {
                _birthdayTabActive.value = it
            }
        }
    }

    suspend fun setBirthdayScreenTab(isActive: Boolean) {
        dataStorePreferenceRepository.setBirthdayScreenTab(isActive)
    }

    suspend fun setActiveTheme(themeName: String) {
        dataStorePreferenceRepository.setActiveTheme(themeName = themeName)
    }

    suspend fun setStartupScreen(screenName: String) {
        dataStorePreferenceRepository.setStartupScreen(screenName = screenName)
    }
}