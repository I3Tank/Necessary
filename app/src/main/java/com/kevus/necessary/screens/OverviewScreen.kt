package com.kevus.necessary.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kevus.necessary.models.Task
import com.kevus.necessary.models.getTestTaskList
import com.kevus.necessary.widgets.TaskBox

@Composable
fun OverviewScreen(TaskList: List<Task>){
    MainContent(TaskList = TaskList){

    }
}

@Composable
private fun MainContent(TaskList: List<Task>, content: @Composable () -> Unit){
    LazyColumn(){
        //we need to order the list by the TaskDate
        items(TaskList.sortedBy { it.TaskDate }) { Task ->
            TaskBox(Task = Task)
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
