package com.kevus.necessary.widgets

import android.graphics.Paint
import android.graphics.Typeface.BOLD
import android.text.SpannableStringBuilder
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.bold
import androidx.core.text.color
import androidx.core.text.scale
import com.kevus.necessary.models.Task
import com.kevus.necessary.models.getTestTask
import com.kevus.necessary.models.getTestTaskList
import java.time.LocalTime

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
        .height(100.dp)
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
fun Timeline(TaskList : List<Task>){
    var sortedTaskList = TaskList.sortedBy { it.TaskDate }

    var dayStart = 7
    var dayEnd = 22

    Column(){
        for (i in dayStart..dayEnd){
            TimeBox(Time = i)
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
    //TaskBox(Task = getTestTask())
    Timeline(TaskList = getTestTaskList())
    //TimeBox(Time = 10)
}
