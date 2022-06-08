package com.kevus.necessary.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kevus.necessary.viemodels.TaskViewModel
import com.kevus.necessary.models.Task
import com.kevus.necessary.models.getTestTask
import com.kevus.necessary.widgets.BottomNavBar
import com.kevus.necessary.widgets.TaskBox
import com.kevus.necessary.widgets.TopDateBar

@Composable
fun OverviewScreen(navController: NavController, taskViewModel: TaskViewModel) {
    val tasks: List<Task> by taskViewModel.tasks.collectAsState()
    //taskViewModel.addTask(getTestTask())
    taskViewModel.removeAllTasks()
    Scaffold(
        backgroundColor = Color.DarkGray,
        topBar = {
            TopDateBar()
        },
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) {
        MainContent(TaskList = tasks)
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
                TaskBox(task = Task)
                //Text(text = Task.TaskName)
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
    OverviewScreen(navController = rememberNavController(), taskViewModel = viewModel())
}
