package com.kevus.necessary.viemodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kevus.necessary.repositories.DataStorePreferenceRepository

class DataStoreViewModelFactory(private val dataStorePreferenceRepository: DataStorePreferenceRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DataStoreViewModel::class.java)) {
            return DataStoreViewModel(dataStorePreferenceRepository) as T
        }
        throw IllegalStateException()
    }
}