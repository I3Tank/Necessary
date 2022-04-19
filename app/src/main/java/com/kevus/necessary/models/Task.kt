@file:Suppress("DEPRECATION")

package com.kevus.necessary.models


import androidx.compose.runtime.Composable
import java.time.LocalDateTime
import java.util.*

//The task will be the "box" visible in the OverviewScreen
data class Task(
    val TaskName: String,
    val TaskDate: LocalDateTime,
    val TaskDuration: Int,
    val TaskDescription: String,
    val TaskTags: List<Tag>,
    val TaskIsDone: Boolean
)

@Composable
fun getTestTask(): Task {
    return Task(
        TaskName = "Self Care",
        TaskDate = LocalDateTime.of(2022, 4, 19, 21,0),
        TaskDuration = 90,
        TaskDescription = "Use the black mask",
        TaskTags = listOf(Tag("Essential"), Tag("Long")),
        TaskIsDone = false
    )
}