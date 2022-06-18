package com.kevus.necessary.screens

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kevus.necessary.viemodels.TaskViewModel
import com.kevus.necessary.widgets.SimpleTopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.kevus.necessary.db.TasksDao
import com.kevus.necessary.models.Task
import com.kevus.necessary.models.calendar
import com.kevus.necessary.ui.theme.NecessaryTheme
import com.kevus.necessary.viemodels.DataStoreViewModel
import java.util.*
//TODO ID as parameter wenn null -> neuer task, sonst bearbeiten/löschen option
@Composable
fun ConfigureTaskScreen(navController: NavController, taskViewModel: TaskViewModel, dataStoreViewModel: DataStoreViewModel, taskId: Long?) {
    val newTask: Boolean = taskId == 0.toLong()

    NecessaryTheme(darkTheme = true, dataStoreViewModel = dataStoreViewModel) {
        Scaffold(
//            backgroundColor = Color.DarkGray,
            topBar = {
                SimpleTopAppBar(arrowBackClicked = { navController.popBackStack() }) {
                    if (newTask) {
                        Text(text = "Add task")
                    } else {
                        Text(text = "Edit task")
                    }
                }
            }
        ) {
            if (newTask) {
                ShowAddTaskMenu(navController = navController, taskViewModel = taskViewModel)
            } else {
                ShowEditTaskMenu(
                    navController = navController,
                    taskViewModel = taskViewModel,
                    taskId = taskId
                )
            }
        }
    }
}

@Composable
fun ShowAddTaskMenu(navController: NavController, taskViewModel: TaskViewModel) {
    Column {
        var taskName by remember { mutableStateOf("") }
        //------------------------------------------------------------------------------------------
        //Task Name Field
        OutlinedTextField(
            value = taskName,
            onValueChange = { value -> taskName = value },
            label = { Text(text = "Task Name") })
        //------------------------------------------------------------------------------------------
        //Date Picker
        //code from: https://www.geeksforgeeks.org/date-picker-in-android-using-jetpack-compose/
        val mContext = LocalContext.current
        val mYear: Int
        val mMonth: Int
        val mDay: Int

        // Initializing a Calendar
        val mCalendar = Calendar.getInstance()

        // Fetching current year, month and day
        mYear = mCalendar.get(Calendar.YEAR)
        mMonth = mCalendar.get(Calendar.MONTH)
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
        // Setting the Calendar to current Date
        mCalendar.time = Date()
        // Declaring a string value to
        // store date in string format
        val displayDate = remember { mutableStateOf("$mDay/${mMonth + 1}/$mYear") }
        val mDate = remember { mutableStateOf(mCalendar.timeInMillis) }
        // Declaring DatePickerDialog and setting
        // initial values as current values (present year, month and day)
        val mDatePickerDialog = DatePickerDialog(
            mContext,
            { _: DatePicker, mYear: Int, mMonth: Int, mDay: Int ->
                mCalendar.set(mYear, mMonth, mDay)
                displayDate.value = "$mDay/${mMonth + 1}/$mYear"
                mDate.value = mCalendar.timeInMillis
            }, mYear, mMonth, mDay
        )
        Row {
            Text(text = displayDate.value)
            Button(
                onClick = { mDatePickerDialog.show() },
                content = { Text(text = "Date") }
            )
        }
        //------------------------------------------------------------------------------------------
        //Start Time Field
        var taskStartTime by remember { mutableStateOf("") }

        OutlinedTextField(
            value = taskStartTime,
            //TODO texteingabe überprüfen
            onValueChange = { value -> taskStartTime = value },
            label = { Text(text = "Start") },
            placeholder = { Text(text = "08:30") }
        )
        //------------------------------------------------------------------------------------------
        //Duration Field
        var taskDuration by remember { mutableStateOf("") }

        OutlinedTextField(value = taskDuration,
            onValueChange = { value -> taskDuration = value },
            label = { Text(text = "Duration") },
            placeholder = { Text(text = "120") })
        //------------------------------------------------------------------------------------------
        //Save Button
        //val calendar: Calendar = Calendar.getInstance()
        //mCalendar.set(mYear, mMonth, mDay)
        //Save button
        //TODO figure out required fields
        mCalendar.timeInMillis = mDate.value

        Button(onClick = {
            taskViewModel.addTask(
                Task(
                    TaskName = taskName,
                    TaskDate = mCalendar.timeInMillis,
                    TaskTime = taskStartTime,
                    TaskDuration = taskDuration.toInt(),
                    TaskDescription = "Added via App",
                    TaskIsDone = false
                )
            )
            navController.popBackStack()
        }, content = { Text(text = "Save") })
    }
}

@Composable
fun ShowEditTaskMenu(navController: NavController, taskViewModel: TaskViewModel, taskId: Long?){
    Column {
        val currentWeekTasks: List<Task> by taskViewModel.weekTasks.collectAsState()
        val task = currentWeekTasks.find { it.id == taskId }
        if(task != null) {
            var taskName by remember { mutableStateOf(task.TaskName) }

            //------------------------------------------------------------------------------------------
            //Task Name Field
            OutlinedTextField(
                value = taskName,
                onValueChange = { value -> taskName = value },
                label = { Text(text = "Task Name") })
            //------------------------------------------------------------------------------------------
            //Date Picker
            //code from: https://www.geeksforgeeks.org/date-picker-in-android-using-jetpack-compose/
            val mContext = LocalContext.current
            val mYear: Int
            val mMonth: Int
            val mDay: Int

            // Initializing a Calendar
            val mCalendar = Calendar.getInstance()

            // Fetching current year, month and day
            mYear = mCalendar.get(Calendar.YEAR)
            mMonth = mCalendar.get(Calendar.MONTH)
            mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
            // Setting the Calendar to the taskDate
            mCalendar.timeInMillis = task.TaskDate

            // Declaring a string value to
            // store date in string format
            val displayDate = remember { mutableStateOf("$mDay/${mMonth + 1}/$mYear") }
            val mDate = remember { mutableStateOf(0L) }

            // Declaring DatePickerDialog and setting
            // initial values as current values (present year, month and day)
            val mDatePickerDialog = DatePickerDialog(
                mContext,
                { _: DatePicker, mYear: Int, mMonth: Int, mDay: Int ->
                    displayDate.value = "$mDay/${mMonth + 1}/$mYear"
                    mCalendar.set(mYear, mMonth, mDay)
                    mDate.value = mCalendar.timeInMillis
                }, mYear, mMonth, mDay
            )
            Row {
                Text(text = displayDate.value)
                Button(
                    onClick = { mDatePickerDialog.show() },
                    content = { Text(text = "Date") }
                )
            }
            //------------------------------------------------------------------------------------------
            //Start Time Field
            var taskStartTime by remember { mutableStateOf(task.TaskTime) }

            OutlinedTextField(
                value = taskStartTime,
                //TODO texteingabe überprüfen
                onValueChange = { value -> taskStartTime = value },
                label = { Text(text = "Start") },
                placeholder = { Text(text = "08:30") }
            )
            //------------------------------------------------------------------------------------------
            //Duration Field
            var taskDuration by remember { mutableStateOf(task.TaskDuration.toString()) }

            OutlinedTextField(value = taskDuration,
                onValueChange = { value -> taskDuration = value },
                label = { Text(text = "Duration") },
                placeholder = { Text(text = "120") })
            //------------------------------------------------------------------------------------------
            //Save Button
            mCalendar.timeInMillis = mDate.value
            //Save button
            //TODO figure out required fields
            Row {
                Button(onClick = {
                    taskViewModel.editTask(
                        Task(
                            id = taskId,
                            TaskName = taskName,
                            TaskDate = mCalendar.timeInMillis,
                            TaskTime = taskStartTime,
                            TaskDuration = taskDuration.toInt(),
                            TaskDescription = "Added via App",
                            TaskIsDone = false
                        )
                    )
                    navController.popBackStack()
                }, content = { Text(text = "Update") })

                Button(onClick = {
                    taskViewModel.removeTask(task)
                    navController.popBackStack()
                },
                    content = { Text(text = "Remove")}
                )
            }
        }
    }
}

