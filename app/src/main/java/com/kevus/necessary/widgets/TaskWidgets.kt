package com.kevus.necessary.widgets

import android.graphics.Paint
import android.graphics.Typeface.BOLD
import android.text.SpannableStringBuilder
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.text.bold
import androidx.core.text.color
import androidx.core.text.scale
import com.kevus.necessary.models.Task
import com.kevus.necessary.models.getTestTask
import com.kevus.necessary.models.getTestTaskList
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

@Composable
fun TaskBox(Task: Task) {
    val endTime = calculateEndTime(Task)

    Card(
        modifier = Modifier
            .width(200.dp)
            .height(Task.TaskDuration.dp)
            .padding(2.5.dp),
        shape = RoundedCornerShape(corner = CornerSize(1.dp)),
    ) {
        Column(
            modifier = Modifier.padding(5.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = Task.TaskName,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.h6
            )
            Text(
                text = Task.TaskDate.toLocalTime().toString() + " - " + endTime.toString(),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.overline
            )
        }
    }
}
@Composable
fun TimeBox(Time: Int){
    var fontSize = 25.sp

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
            if(number == 12){
                    TaskBox(Task = getTestTask())
            }
        }
    }
}


private fun calculateEndTime(Task: Task): LocalTime {
    return Task.TaskDate.toLocalTime().plusMinutes(Task.TaskDuration.toLong())
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
    DisplayThingy()
}
