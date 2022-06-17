@file:Suppress("DEPRECATION")

package com.kevus.necessary.models


import androidx.compose.runtime.Composable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.*

//The task will be the "box" visible in the OverviewScreen
@Entity(tableName = "Tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    val TaskName: String,
    ////Format: yyyy-mm-dd, 2022-06-13
    ////TaskDate Format = yyyy-mm-ddThh:mm
    //val TaskDate: String,
    val TaskDate: Long,
    //Format: hh:mm
    val TaskTime: String,
    val TaskDuration: Int,
    val TaskDescription: String,
    //Format: tagName-tagName-...
    val TaskTags: String? = null,
    val TaskIsDone: Boolean
)
var calendar: Calendar = Calendar.getInstance()
@Composable
fun getTestTask(): Task {
    calendar.set(2022, 6, 13)
    return Task(
        TaskName = "Self Care",
        //TaskDate = "2022-06-13",
        TaskDate = calendar.timeInMillis,
        TaskTime = "22:20",
        TaskDuration = 90,
        TaskDescription = "Use the black mask",
        TaskTags = "testTag1-testTag2",
        TaskIsDone = false
    )
}

//@Composable
//fun getTestTaskList() : List<Task>{
//    return listOf(
//        Task(
//            TaskName = "Breakfast",
//            TaskDate = "2022-06-13",
//            TaskTime = "09:45",
//            TaskDuration = 45,
//            TaskDescription = "Make and eat breakfast",
//            TaskIsDone = false
//        ),
//        Task(
//            TaskName = "Homework",
//            TaskDate = "2022-06-13",
//            TaskTime = "12:45",
//            TaskDuration = 90,
//            TaskDescription = "Do math homework",
//            TaskIsDone = false
//        ),
//        Task(
//            TaskName = "Gaming",
//            TaskDate = "2022-06-13",
//            TaskTime = "20:45",
//            TaskDuration = 120,
//            TaskDescription = "Gaming with the bois",
//            TaskIsDone = false
//        ),
//        Task(
//            TaskName = "Breakfast2",
//            TaskDate = "2022-06-14",
//            TaskTime = "09:45",
//            TaskDuration = 45,
//            TaskDescription = "Make and eat breakfast",
//            TaskIsDone = false
//        ),
//        Task(
//            TaskName = "Breakfast",
//            TaskDate = "2022-06-15",
//            TaskTime = "10:45",
//            TaskDuration = 45,
//            TaskDescription = "Make and eat breakfast",
//            TaskIsDone = false
//        ),
//        Task(
//            TaskName = "Meal",
//            TaskDate = "2022-06-16",
//            TaskTime = "12:45",
//            TaskDuration = 45,
//            TaskDescription = "Make and eat breakfast",
//            TaskIsDone = false
//        ),
//    )
//}