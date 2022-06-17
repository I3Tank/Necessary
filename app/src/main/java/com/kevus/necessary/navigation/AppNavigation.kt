package com.kevus.necessary.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kevus.necessary.viemodels.TaskViewModel
import com.kevus.necessary.db.TasksDB
import com.kevus.necessary.repositories.TaskRepository
import com.kevus.necessary.screens.ConfigureTaskScreen
import com.kevus.necessary.screens.OverviewScreen
import com.kevus.necessary.screens.SettingsScreen
import com.kevus.necessary.screens.TaskListScreen
import com.kevus.necessary.viemodels.TaskViewModelFactory

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current
    val db = TasksDB.getDataBase(context = context)
    val repository = TaskRepository(dao = db.tasksDao())

    val taskViewModel: TaskViewModel = viewModel(
        factory = TaskViewModelFactory(repository = repository)
    )

    NavHost(navController = navController, startDestination = AppScreens.OverviewScreen.name) {
        composable(route = AppScreens.OverviewScreen.name) {
            OverviewScreen(navController = navController, taskViewModel = taskViewModel)
        }

        composable(route = AppScreens.TaskListScreen.name) {
            TaskListScreen(navController = navController, taskViewModel = taskViewModel)
        }

        composable(route = AppScreens.SettingsScreen.name) {
            SettingsScreen(navController = navController, taskViewModel = taskViewModel)
        }
        //if taskId = null -> create a new Task, otherwise edit/delete Task
        composable(
            route = AppScreens.ConfigureTaskScreen.name + "/{taskId}",
            arguments = listOf(navArgument(name = "taskId") {
                type = NavType.LongType
            })
        ) { navBackStackEntry ->
            ConfigureTaskScreen(
                navController = navController,
                taskViewModel = taskViewModel,
                taskId = navBackStackEntry.arguments?.getLong("taskId")
            )
        }
    }
}