package com.kevus.necessary.viemodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevus.necessary.models.Birthday
import com.kevus.necessary.models.Task
import com.kevus.necessary.repositories.BirthdayRepository
import com.kevus.necessary.repositories.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class BirthdayViewModel(
    private val repository: BirthdayRepository
) : ViewModel(){

    private var _birthdays = MutableStateFlow<List<Birthday>>(emptyList())
    val birthdays = _birthdays.asStateFlow()

    init {
        getAllBirthdays()
    }

    fun getAllBirthdays(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllBirthdays().collect{ listOfBirthdays ->
                if(listOfBirthdays.isNullOrEmpty()){
                    Log.d("BirthdayViewModel", "No Birthdays")
                } else {
                    _birthdays.value = listOfBirthdays
                }
            }
        }
    }

    fun addBirthday(birthday: Birthday){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBirthday(birthday = birthday)
        }
    }

    fun removeBirthday(birthday: Birthday){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteBirthday(birthday = birthday)
        }
    }

    fun editBirthday(birthday: Birthday){
        viewModelScope.launch(Dispatchers.IO) {
            repository.editBirthday(birthday = birthday)
        }
    }
}