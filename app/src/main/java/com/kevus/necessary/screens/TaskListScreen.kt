package com.kevus.necessary.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.kevus.necessary.models.Task
import com.kevus.necessary.navigation.AppScreens
import com.kevus.necessary.viemodels.TaskViewModel
import com.kevus.necessary.widgets.TaskBox

@Composable
fun TaskListScreen(navController: NavController, taskViewModel: TaskViewModel) {
    Scaffold(
        backgroundColor = Color.DarkGray,
        topBar = {
            TopAppBar(title = { Text(text = "Tasklist") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(AppScreens.ConfigureTaskScreen.name)
            }, content = {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Add new task"
                )
            })
        }
    ) {
        MainContent(navController = navController, taskViewModel = taskViewModel)
    }
}

@Composable
private fun MainContent(navController: NavController, taskViewModel: TaskViewModel) {
    val taskList: List<Task> by taskViewModel.tasks.collectAsState()

    //Timeline()
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        //we need to order the list by the TaskDate
        items(taskList.sortedBy { it.TaskDate }) { Task ->
            TaskBox(task = Task)
            //Text(text = Task.TaskName)
        }
    }

}