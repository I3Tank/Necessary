package com.kevus.necessary.screens

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Column
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
import java.util.*

@Composable
fun ConfigureTaskScreen(navController: NavController, taskViewModel: TaskViewModel){
    Scaffold(
        backgroundColor = Color.DarkGray,
        topBar = {
            SimpleTopAppBar(arrowBackClicked = {navController.popBackStack()}){
                Text(text = "Add task")
            }
        }
    ) {
        MainContent(taskViewModel)
    }
}

//TODO: Refactor this abomination
@Composable
private fun MainContent(taskViewModel: TaskViewModel) {
    Column() {
        // Input field for our Task name
        var taskName by remember { mutableStateOf("") }

        OutlinedTextField(value = taskName, onValueChange = { value -> taskName = value },
            label = { Text(text = "Task Name") })

        val mContext = LocalContext.current

        val mYear: Int
        var mMonth: Int
        var mDay: Int

        // Initializing a Calendar
        val mCalendar = Calendar.getInstance()

        // Fetching current year, month and day
        mYear = mCalendar.get(Calendar.YEAR)
        mMonth = mCalendar.get(Calendar.MONTH)
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

        mCalendar.time = Date()

        // Declaring a string value to
        // store date in string format
        val mDate = remember { mutableStateOf("") }

        // Declaring DatePickerDialog and setting
        // initial values as current values (present year, month and day)
        val mDatePickerDialog = DatePickerDialog(
            mContext,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                mDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
            }, mYear, mMonth, mDay
        )

        Button(onClick = { mDatePickerDialog.show() }, content = { Text(text = "Day") })

        // Input field for our Task start
        var taskStartTime by remember { mutableStateOf("") }

        OutlinedTextField(value = taskStartTime, onValueChange = { value -> taskStartTime = value },
            label = { Text(text = "Start") })

        // Input field for our Task duration
        var taskDuration by remember { mutableStateOf("") }

        OutlinedTextField(value = taskDuration, onValueChange = { value -> taskDuration = value },
            label = { Text(text = "Duration") })

        // We need the Month and Day in the format "0X"
        var mMonthString = mMonth.toString()
        var mDayString = mDay.toString()
        if(mMonth.toString().length == 1) {mMonthString = "0$mMonth"}
        if(mDay.toString().length == 1) {mDayString = "0$mDay"}
        //Save button
        Button(onClick = {
            taskViewModel.addTask(
                Task(
                    //TODO Figure out the ID system
                    id = 10,
                    TaskName = taskName,
                    TaskDate = "$mYear-$mMonthString-$mDayString" + "T$taskStartTime",
                    TaskDuration = taskDuration.toInt(),
                    TaskDescription = "Added via App",
                    TaskIsDone = false
                )
            )
        }, content = { Text(text = "Save") })

    }
}

