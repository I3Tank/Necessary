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
    val TaskDate: String,
    val TaskDuration: Int,
    val TaskDescription: String,
    //val TaskTags: List<Tag>,
    val TaskIsDone: Boolean
)

@Composable
fun getTestTask(): Task {
    return Task(
        TaskName = "Self Care",
        //TaskDate = LocalDateTime.of(2022, 4, 19, 21,0),
        //TaskDate = "2022-05-06T21:00",
        TaskDate = "2016-12-08T22:20",
        TaskDuration = 90,
        TaskDescription = "Use the black mask",
        //TaskTags = listOf(Tag("Essential"), Tag("Long")),
        TaskIsDone = false
    )
}

@Composable
fun getTestTaskList() : List<Task>{
    return listOf(
        Task(
            TaskName = "Self Care",
//            TaskDate = LocalDateTime.of(2022, 4, 19, 21,0),
            TaskDate = "2016-12-08T22:20",
            TaskDuration = 90,
            TaskDescription = "Use the black mask",
//            TaskTags = listOf(Tag("Essential"), Tag("Long")),
            TaskIsDone = false
        ),
        Task(
            TaskName = "Gaming",
//            TaskDate = LocalDateTime.of(2022, 4, 19, 14,30),
            TaskDate = "2016-12-08T22:20",
            TaskDuration = 300,
            TaskDescription = "Game with da bois",
//            TaskTags = listOf(Tag("Essential"), Tag("ExtraLong")),
            TaskIsDone = false
        ),
//        Task(
//            TaskName = "Gaming",
//            TaskDate = LocalDateTime.of(2022, 4, 19, 14,30),
//            TaskDuration = 300,
//            TaskDescription = "Game with da bois",
//            TaskTags = listOf(Tag("Essential"), Tag("ExtraLong")),
//            TaskIsDone = false
//        ),
//        Task(
//            TaskName = "Gaming",
//            TaskDate = LocalDateTime.of(2022, 4, 19, 14,30),
//            TaskDuration = 300,
//            TaskDescription = "Game with da bois",
//            TaskTags = listOf(Tag("Essential"), Tag("ExtraLong")),
//            TaskIsDone = false
//        ),
//        Task(
//            TaskName = "Schlafen",
//            TaskDate = LocalDateTime.of(2022, 4, 19, 8,45),
//            TaskDuration = 15,
//            TaskDescription = " ",
//            TaskTags = listOf(Tag("Not important"), Tag("Short")),
//            TaskIsDone = false
//        ),
//        Task(
//            TaskName = "FH",
//            TaskDate = LocalDateTime.of(2022, 4, 19, 12,45),
//            TaskDuration = 60,
//            TaskDescription = "In this task we will try to improve our work flow. And also this is a veeeeeeeeeeery long task description for our not so long taaaaaaaaaaaask.",
//            TaskTags = listOf(Tag("Not important"), Tag("Short")),
//            TaskIsDone = false
//        )

    )
}