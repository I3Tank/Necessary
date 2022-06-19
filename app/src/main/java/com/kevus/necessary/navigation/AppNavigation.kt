package com.kevus.necessary.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kevus.necessary.db.BirthdaysDB
import com.kevus.necessary.db.TasksDB
import com.kevus.necessary.repositories.BirthdayRepository
import com.kevus.necessary.repositories.TaskRepository
import com.kevus.necessary.screens.*
import com.kevus.necessary.viemodels.*

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController(), dataStoreViewModel: DataStoreViewModel) {
    val context = LocalContext.current
    val taskDB = TasksDB.getDataBase(context = context)
    val taskRepository = TaskRepository(dao = taskDB.tasksDao())

    val taskViewModel: TaskViewModel = viewModel(
        factory = TaskViewModelFactory(repository = taskRepository)
    )

    val birthdayDB = BirthdaysDB.getDataBase(context = context)
    val birthdayRepository = BirthdayRepository(dao = birthdayDB.birthdayDao())
    val birthdayViewModel: BirthdayViewModel = viewModel(
        factory = BirthdayViewModelFactory(repository = birthdayRepository)
    )

    val startupScreen = dataStoreViewModel.startupScreen.observeAsState().value
    //val dataStoreRepository: DataStorePreferenceRepository = DataStorePreferenceRepository.getInstance(context)

//    val dataStoreViewModel: DataStoreViewModel = viewModel(
//        factory = DataStoreViewModelFactory(DataStorePreferenceRepository(LocalContext.current))
//    )

    NavHost(navController = navController, startDestination = startupScreen!!) {
        composable(route = AppScreens.OverviewScreen.name) {
            OverviewScreen(navController = navController, taskViewModel = taskViewModel, dataStoreViewModel = dataStoreViewModel)
        }

        composable(route = AppScreens.TaskListScreen.name) {
            TaskListScreen(navController = navController, taskViewModel = taskViewModel, dataStoreViewModel = dataStoreViewModel)
        }

        composable(route = AppScreens.SettingsScreen.name) {
            SettingsScreen(navController = navController, dataStoreViewModel = dataStoreViewModel)
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
                dataStoreViewModel = dataStoreViewModel,
                taskViewModel = taskViewModel,
                taskId = navBackStackEntry.arguments?.getLong("taskId")
            )
        }

        composable(route = AppScreens.BirthdayReminderScreen.name) {
            BirthdayReminderScreen(navController = navController, taskViewModel = taskViewModel, dataStoreViewModel = dataStoreViewModel, birthdayViewModel = birthdayViewModel)
        }
    }
}