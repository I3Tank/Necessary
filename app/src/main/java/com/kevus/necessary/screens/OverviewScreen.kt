package com.kevus.necessary.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.kevus.necessary.models.Task
import com.kevus.necessary.models.getTestTaskList
import com.kevus.necessary.widgets.SimpleTopAppBar
import com.kevus.necessary.widgets.TaskBox
import com.kevus.necessary.widgets.TopDateBar

@Composable
fun OverviewScreen(TaskList: List<Task>) {
    Scaffold(
        backgroundColor = Color.DarkGray,
        topBar = {
            TopDateBar()
        }
    ) {
        MainContent(TaskList = TaskList)
    }
}

@Composable
private fun MainContent(TaskList: List<Task>){
    Row(
    ) {
        //Timeline()
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            //we need to order the list by the TaskDate
            items(TaskList.sortedBy { it.TaskDate }) { Task ->
                TaskBox(Task = Task)
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    backgroundColor = 0x3D4849,
)
@Composable
fun DefaultPreview(){
    OverviewScreen(getTestTaskList())
}
