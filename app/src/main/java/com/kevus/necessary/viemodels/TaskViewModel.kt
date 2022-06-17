package com.kevus.necessary.viemodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevus.necessary.models.Task
import com.kevus.necessary.repositories.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class TaskViewModel(
    private val repository: TaskRepository
) : ViewModel(){


    private var _dayTasks = MutableStateFlow<List<Task>>(emptyList())
    val dayTasks = _dayTasks.asStateFlow()
    //To use the taskFlow as list:
    // val tasks: List<Task> by viewModel.notes.collectAsState()

    private var _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks = _tasks.asStateFlow()

//    //TODO: Not as list but as single object!!!
//    private var _taskById = MutableStateFlow<List<Task>>(emptyList())
//    val taskById = _taskById.asStateFlow()

    private var _weekTasks = MutableStateFlow<List<Task>>(emptyList())
    val weekTasks = _weekTasks.asStateFlow()

    init {
        //at the start of our app we need to load all the tasks of the week
        val calendar = Calendar.getInstance()
        //set the first day of the week to monday, otherwise it starts with sunday
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        val currentMonday =  calendar.timeInMillis
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        val currentSunday =  calendar.timeInMillis

        getTasksBetweenDate(currentMonday, currentSunday)
    }

    fun getAllTasks(){
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

    fun getTasksBetweenDate(startDate: Long, endDate: Long){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTasksBetweenDates(startDate = startDate, endDate = endDate).collect(){ listOfTasks ->
                if(listOfTasks.isNullOrEmpty()){
                    Log.d("TaskViewModel", "No Tasks")
                } else {
                    _weekTasks.value = listOfTasks
                }
            }
        }
    }

    fun getTasksByDate(date: Long){
        viewModelScope.launch(Dispatchers.IO){
            repository.getTasksByDate(date = date).collect(){ listOfTasks ->
                if(listOfTasks.isNullOrEmpty()){
                    Log.d("TaskViewModel", "No Tasks")
                } else {
                    _dayTasks.value = listOfTasks
                }
            }
        }
    }

//    fun getTaskById(taskId: Long?){
//        viewModelScope.launch(Dispatchers.IO){
//            repository.getTaskById(taskId = taskId).collect(){ listOfTasks ->
//                if(listOfTasks.isNullOrEmpty()){
//                    Log.d("TaskViewModel", "No Tasks")
//                } else {
//                    _taskById.value = listOfTasks
//                }
//            }
//        }
//    }

    fun addTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(task = task)
        }
    }

    fun removeTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task = task)
        }
    }

    fun editTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            repository.editTask(task = task)
        }
    }


    fun removeAllTasks(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

}