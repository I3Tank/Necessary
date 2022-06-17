package com.kevus.necessary.repositories

import com.kevus.necessary.db.TasksDao
import com.kevus.necessary.models.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val dao: TasksDao) {

    fun addTask(task: Task) = dao.addTask(task = task)

    fun editTask(task: Task) = dao.editTask(task = task)

    fun deleteTask(task: Task) = dao.deleteTask(task = task)

    fun deleteAll() = dao.deleteAll()

    fun getTaskById(taskId: Long): Flow<List<Task>> = dao.getTaskById(taskId = taskId)

    fun getAllTasks(): Flow<List<Task>> = dao.getTasks()

    fun getTasksByDate(date: Long) = dao.getTasksByDate(date = date)

    fun getTasksBetweenDates(startDate: Long, endDate: Long): Flow<List<Task>> = dao.getTasksBetweenDates(startDate = startDate, endDate = endDate)
}