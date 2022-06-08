package com.kevus.necessary.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.kevus.necessary.models.Task
import com.kevus.necessary.models.getTestTask
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME

@Composable
fun TaskBox(task: Task) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(task.TaskDuration.dp)
            .padding(2.5.dp),
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
                style = MaterialTheme.typography.h6
            )
            //Text for the displayed Time
            Text(
                text = getDisplayTime(task),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.overline
            )
        }
    }
}
@Composable
fun TimeBox(Time: Int){
    val fontSize = 25.sp

    Box(modifier = Modifier
        .height(30.dp)
        .width(50.dp)
        .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 4.dp)
        ) {

            Row() {
                Text(
                    text = Time.toString(),
                    fontSize = fontSize
                )
                Text(
                    text = "00",
                    fontSize = (fontSize / 2),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun DisplayThingy(){
    //var sortedTaskList = TaskList.sortedBy { it.TaskDate }

    val dayStart = 7
    val dayEnd = 22
    val dayDuration = dayEnd - dayStart
    val numberOfSlots = dayDuration * 4

    val maxZValue = numberOfSlots

    val slotList = listOf<Int>(
        1,2,3,4,5,6,7,8,9,10
    )

//    Column(){
//        for (i in 0..numberOfSlots){
//            //TimeBox(Time = i)
//            TaskSlot(number = i, (maxZValue - i).toFloat())
//        }
//    }
    LazyColumn(){
        items(slotList){ slot ->
            TaskSlot(number = slot, zValue = (maxZValue - slot).toFloat())
        }
    }
}

@Composable
fun TaskSlot(number: Int, zValue: Float){
    Box(
        modifier = Modifier
            .height(15.dp)
            .fillMaxWidth()
            .background(Color.White)
            .wrapContentHeight(unbounded = true)
            .zIndex(
                (if (number % 4 == 0) {
                    zValue
                } else {
                    0f
                })
            )
            .defaultMinSize(Dp.Unspecified, 15.dp)
    ) {
        Row() {
            if(number % 4 == 0) {
                TimeBox(Time = number / 4)
            }
            if(number == 9){
                TaskBox(task = getTestTask())
            }
        }
    }
}

private fun getDisplayTime(task: Task): String {
    //k = hours (1-24), m = minutes
    val formatter = DateTimeFormatter.ofPattern("k:m")

    //add the duration of the task to the time to get the range (start - end)
    val startTime = LocalDateTime.parse(task.TaskDate, ISO_LOCAL_DATE_TIME)
    val endTime = startTime.plusMinutes(task.TaskDuration.toLong())

    return startTime.format(formatter) + " - " + endTime.format(formatter)
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    backgroundColor = 0x3D4849,
)
@Composable
fun DefaultPreview(){
    //TopDateBar()
    //TaskBox(Task = getTestTask())
    //Timeline(TaskList = getTestTaskList())
    //TimeBox(Time = 10)
    //DisplayThingy()
}
