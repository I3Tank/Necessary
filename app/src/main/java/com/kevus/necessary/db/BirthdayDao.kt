package com.kevus.necessary.db

import androidx.room.*
import com.kevus.necessary.models.Birthday
import com.kevus.necessary.models.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface BirthdayDao {

    @Insert
    fun addBirthday(birthday: Birthday)

    @Update
    fun editBirthday(birthday: Birthday)

    @Delete
    fun deleteBirthday(birthday: Birthday)

    @Query("SELECT * FROM BIRTHDAYS")
    fun getAllBirthdays(): Flow<List<Birthday>>


}