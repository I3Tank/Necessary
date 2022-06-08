package com.kevus.necessary.viemodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevus.necessary.models.Task
import com.kevus.necessary.repositories.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: TaskRepository
) : ViewModel(){
    private var _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks = _tasks.asStateFlow()
    //To use the taskFlow as list:
    // val tasks: List<Task> by viewModel.notes.collectAsState()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllTasks().collect{ listOfTasks ->
                if(listOfTasks.isNullOrEmpty()){
                    Log.d("TaskViewModel", "No Tasks")
                } else {
                    _tasks.value = listOfTasks
                }
            }
        }
    }

    fun addTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(task = task)
        }
    }

    fun removeTask(task: Task){
        repository.deleteTask(task = task)
    }

    fun editTask(task: Task){
        repository.editTask(task = task)
    }

    fun removeAllTasks(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

}