package com.kevus.necessary.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kevus.necessary.models.Task
import com.kevus.necessary.models.getTestTask
import java.time.LocalTime

@Composable
fun TaskBox(Task: Task){
    val endTime = calculateEndTime(Task)

    Card(shape = RoundedCornerShape(corner = CornerSize(10.dp))) {
         Column(modifier = Modifier.padding(5.dp)) {
             Text(text = Task.TaskName)
             Text(text = Task.TaskDescription)
             Text(text = Task.TaskDate.toLocalTime().toString() + " - " + endTime.toString())
         }
    }
}

private fun calculateEndTime(Task: Task): LocalTime {
    return Task.TaskDate.toLocalTime().plusMinutes(Task.TaskDuration.toLong())
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    TaskBox(Task = getTestTask())
}
