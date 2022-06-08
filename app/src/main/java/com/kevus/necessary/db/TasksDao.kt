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
    fun getNotes(): Flow<List<Task>>

    @Query("DELETE FROM Tasks")
    fun deleteAll()

    @Query("SELECT * FROM Tasks WHERE id=:id")
    fun getNoteById(id: Long): Task

}