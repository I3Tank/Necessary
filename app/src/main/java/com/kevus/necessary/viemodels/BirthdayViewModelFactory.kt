package com.kevus.necessary.viemodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kevus.necessary.repositories.BirthdayRepository
import com.kevus.necessary.repositories.TaskRepository
import java.lang.IllegalArgumentException

class BirthdayViewModelFactory(private val repository: BirthdayRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BirthdayViewModel::class.java)){
            return BirthdayViewModel(repository = repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}