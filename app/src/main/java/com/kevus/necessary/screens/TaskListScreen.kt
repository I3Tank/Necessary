package com.kevus.necessary.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.navigation.NavController
import com.kevus.necessary.models.Task
import com.kevus.necessary.navigation.AppScreens
import com.kevus.necessary.ui.theme.NecessaryTheme
import com.kevus.necessary.viemodels.DataStoreViewModel
import com.kevus.necessary.viemodels.TaskViewModel
import com.kevus.necessary.widgets.BottomNavBar
import com.kevus.necessary.widgets.TaskBox

@Composable
fun TaskListScreen(navController: NavController, taskViewModel: TaskViewModel, dataStoreViewModel: DataStoreViewModel) {

    NecessaryTheme(darkTheme = true, dataStoreViewModel = dataStoreViewModel) {
        Scaffold(
//            backgroundColor = Color.DarkGray,
            topBar = {
                TopAppBar(title = {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "TASKLIST",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            style = MaterialTheme.typography.body1,
                            fontStyle = FontStyle.Italic
                        )
                    }
                })
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    //can't use null with the navigation so we use a ID that is never auto Generated(hopefully) = 0
                    val newTaskId: Long = 0
                    navController.navigate(AppScreens.ConfigureTaskScreen.name + "/$newTaskId")
                }, content = {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Add new task"
                    )
                })
            },
            bottomBar = {
                BottomNavBar(navController = navController, dataStoreViewModel = dataStoreViewModel)
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                MainContent(navController = navController, taskViewModel = taskViewModel)
            }
        }
    }
}

@Composable
private fun MainContent(navController: NavController, taskViewModel: TaskViewModel) {
    //val taskList: List<Task> by taskViewModel.tasks.collectAsState()
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