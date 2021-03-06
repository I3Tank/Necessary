package com.kevus.necessary.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kevus.necessary.models.Task
import com.kevus.necessary.navigation.AppScreens
import com.kevus.necessary.ui.theme.NecessaryTheme
import com.kevus.necessary.viemodels.DataStoreViewModel
import com.kevus.necessary.viemodels.TaskViewModel
import com.kevus.necessary.widgets.*
import java.util.*


@Composable
fun OverviewScreen(navController: NavController, taskViewModel: TaskViewModel, dataStoreViewModel: DataStoreViewModel) {

    //True = Daily View, False = Weekly View
    var overviewMode by remember { mutableStateOf(true) }

    NecessaryTheme(darkTheme = true, dataStoreViewModel = dataStoreViewModel) {
        Scaffold(
//            backgroundColor = Color.DarkGray,
            topBar = {
                if (overviewMode) {
                    TopDayBar(onToggleClick = { overviewMode = !overviewMode })
                } else {
                    TopWeekBar(onToggleClick = { overviewMode = !overviewMode })
                }
            },
            bottomBar = {
                BottomNavBar(navController = navController, dataStoreViewModel = dataStoreViewModel)
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                if (overviewMode) {
                    ShowDayOverview(taskViewModel = taskViewModel, navController = navController)
                } else {
                    ShowWeeklyOverview(taskViewModel = taskViewModel)
                }
            }
        }
    }
}

@Composable
fun ShowDayOverview(taskViewModel: TaskViewModel, navController: NavController) {
    val calendar: Calendar = Calendar.getInstance()
    taskViewModel.getTasksByDate(calendar.timeInMillis)
    val currentDayTasks: List<Task> by taskViewModel.dayTasks.collectAsState()
    Row(
    ) {
        //Text(text = currentDate.toString())
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            //we need to order the list by the TaskDate
            items(currentDayTasks.sortedBy { it.TaskTime }) { task ->
                TaskBox(
                    task = task,
                    onItemClick = { taskId -> navController.navigate(AppScreens.ConfigureTaskScreen.name + "/$taskId")
                    }
                )
            }
        }
    }
}

@Composable
fun ShowWeeklyOverview(taskViewModel: TaskViewModel){
    val currentWeekTasks: List<Task> by taskViewModel.weekTasks.collectAsState()
    val calendar = Calendar.getInstance()
    calendar.firstDayOfWeek = Calendar.MONDAY

    val mondayList: MutableList<Task> = mutableListOf()
    val tuesdayList: MutableList<Task> = mutableListOf()
    val wednesdayList: MutableList<Task> = mutableListOf()
    val thursdayList: MutableList<Task> = mutableListOf()
    val fridayList: MutableList<Task> = mutableListOf()
    val saturdayList: MutableList<Task> = mutableListOf()
    val sundayList: MutableList<Task> = mutableListOf()

    //get all monday tasks
//    val mondayTasks = currentWeekTasks.filter { task ->
//        calendar.timeInMillis = task.TaskDate
//
//        calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY
//    }
    currentWeekTasks.forEach { task ->
        calendar.timeInMillis = task.TaskDate
        var day = calendar.get(Calendar.DAY_OF_WEEK)

        when (day) {
            Calendar.MONDAY -> mondayList.add(task)
            Calendar.TUESDAY -> tuesdayList.add(task)
            Calendar.WEDNESDAY -> wednesdayList.add(task)
            Calendar.THURSDAY -> thursdayList.add(task)
            Calendar.FRIDAY -> fridayList.add(task)
            Calendar.SATURDAY -> saturdayList.add(task)
            Calendar.SUNDAY -> sundayList.add(task)
        }
    }
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)) {
        // Here are all the lines of your table.

            Row(Modifier.fillMaxWidth()) {
                dayColumn(mondayList)
                dayColumn(tuesdayList)
                dayColumn(wednesdayList)
                dayColumn(thursdayList)
                dayColumn(fridayList)
                dayColumn(saturdayList)
                dayColumn(sundayList)


        }
//        items(mondayList) { task ->
//            Column() {
//                TableCell
//            }
//        }
    }
    
}
@Composable
fun dayColumn(tasks: List<Task>){
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp / 7
    Column(Modifier.width(screenWidth)) {
        tasks.forEach(){ task ->
            //Text(text = task.TaskName.toCharArray()[0].toString(), Modifier.align(Alignment.CenterHorizontally))
            WeekTaskBox(task = task, boxWidth = screenWidth)
        }
    }
}