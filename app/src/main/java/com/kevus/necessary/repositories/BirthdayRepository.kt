package com.kevus.necessary.repositories

import com.kevus.necessary.db.BirthdayDao
import com.kevus.necessary.db.TasksDao
import com.kevus.necessary.models.Birthday
import com.kevus.necessary.models.Task
import kotlinx.coroutines.flow.Flow

class BirthdayRepository(private val dao: BirthdayDao) {

    fun addBirthday(birthday: Birthday) = dao.addBirthday(birthday = birthday)

    fun editBirthday(birthday: Birthday) = dao.editBirthday(birthday = birthday)

    fun deleteBirthday(birthday: Birthday) = dao.deleteBirthday(birthday = birthday)

    fun getAllBirthdays(): Flow<List<Birthday>> = dao.getAllBirthdays()
}