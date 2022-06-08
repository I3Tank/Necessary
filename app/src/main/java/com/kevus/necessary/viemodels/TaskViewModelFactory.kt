package com.kevus.necessary.viemodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kevus.necessary.repositories.TaskRepository
import java.lang.IllegalArgumentException

class TaskViewModelFactory(private val repository: TaskRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TaskViewModel::class.java)){
            return TaskViewModel(repository = repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}