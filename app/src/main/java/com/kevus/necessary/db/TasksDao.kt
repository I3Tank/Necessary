package com.kevus.necessary.db

import androidx.room.*
import com.kevus.necessary.models.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {

    @Insert
    fun addTask(task: Task)

    @Update
    fun editTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Query("SELECT * FROM Tasks")
    fun getTasks(): Flow<List<Task>>

    @Query("DELETE FROM Tasks")
    fun deleteAll()

    @Query("SELECT * FROM Tasks WHERE id=:taskId")
    fun getTaskById(taskId: Long): Flow<List<Task>>

    //This gets rid of the milliseconds, seconds, minutes and hours, so we just focus on the date
    @Query("SELECT * FROM TASKS WHERE TaskDate / (1000 * 60 * 60 * 24) = (:date/ (1000 * 60 * 60 * 24))")
    fun getTasksByDate(date: Long): Flow<List<Task>>

    @Query("SELECT * FROM TASKS WHERE TaskDate / (1000 * 60 * 60 * 24) BETWEEN (:startDate/ (1000 * 60 * 60 * 24)) AND (:endDate/ (1000 * 60 * 60 * 24))")
    fun getTasksBetweenDates(startDate: Long, endDate: Long): Flow<List<Task>>

}