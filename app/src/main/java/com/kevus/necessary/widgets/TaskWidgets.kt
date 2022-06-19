package com.kevus.necessary.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kevus.necessary.models.Task
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.min

@Composable
fun TaskBox(
    task: Task,
    onItemClick: (Long?) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(task.TaskDuration.dp)
            .padding(2.5.dp)
            .clickable {
                onItemClick(task.id)
            },
        shape = RoundedCornerShape(corner = CornerSize(1.dp)),
    ) {
        Column(
            modifier = Modifier.padding(5.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            //Text for the Task Name
            Text(
                text = task.TaskName,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.body2
            )
            //Text for the displayed Time
            Text(
                text = getDisplayTime(task),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}

@Composable
fun WeekTaskBox(
    task: Task,
    boxWidth: Dp,
    onItemClick: (Long?) -> Unit = {}
) {
    val minHeight = 70
    Card(
        modifier = Modifier
            .width(boxWidth)
            .height(
                if (task.TaskDuration <= minHeight) {
                    minHeight.dp
                } else {
                    task.TaskDuration.dp
                }
            )
            .padding(2.5.dp)
            .clickable {
                onItemClick(task.id)
            },
        shape = RoundedCornerShape(corner = CornerSize(1.dp)),
    ) {
        Column() {
            Text(text = task.TaskName, style = MaterialTheme.typography.subtitle2, maxLines = 1, overflow = TextOverflow.Ellipsis)
            var displayTimeArr = getDisplayTime(task).split('-')
            Text(text = displayTimeArr[0], style = MaterialTheme.typography.subtitle2)
            Divider()
            Text(text = displayTimeArr[1], style = MaterialTheme.typography.subtitle2)
        }
    }
}

private fun getDisplayTime(task: Task): String {
    //k = hours (1-24), m = minutes
    val formatter = DateTimeFormatter.ofPattern("k:mm")

    //add the duration of the task to the time to get the range (start - end)
    val startTime = LocalTime.parse(task.TaskTime)
    val endTime = startTime.plusMinutes(task.TaskDuration.toLong())

    return startTime.format(formatter) + "-" + endTime.format(formatter)
}


@Preview(
    showBackground = true,
    showSystemUi = true,
    backgroundColor = 0x3D4849,
)
@Composable
fun DefaultPreview(){
//    WeekTaskBox(task = Task(
//        TaskName = "Self Care",
//        //TaskDate = "2022-06-13",
//        TaskDate = 1655653068547,
//        TaskTime = "22:20",
//        TaskDuration = 90,
//        TaskDescription = "Use the black mask",
//        TaskTags = "testTag1-testTag2",
//        TaskIsDone = false
//    ), boxWidth = 10.dp)
    //TopDateBar()
    //TaskBox(Task = getTestTask())
    //Timeline(TaskList = getTestTaskList())
    //TimeBox(Time = 10)
    //DisplayThingy()
}
