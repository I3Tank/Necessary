package com.kevus.necessary.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.kevus.necessary.ui.theme.NecessaryTheme
import com.kevus.necessary.viemodels.DataStoreViewModel
import com.kevus.necessary.viemodels.TaskViewModel
import com.kevus.necessary.widgets.BottomNavBar

@Composable
fun BirthdayReminderScreen(navController: NavController, taskViewModel: TaskViewModel, dataStoreViewModel: DataStoreViewModel) {

    NecessaryTheme(darkTheme = true, dataStoreViewModel = dataStoreViewModel) {
        Scaffold(
//            backgroundColor = Color.DarkGray,
            topBar = {
                TopAppBar(title = { Text(text = "Birthdays") })
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    //TODO Add Birthday
                }, content = {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Add new birthday"
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
private fun MainContent(navController: NavController, taskViewModel: TaskViewModel){
    Text(text = "Work in progress!")
}