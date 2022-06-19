package com.kevus.necessary.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kevus.necessary.models.Birthday
import com.kevus.necessary.ui.theme.NecessaryTheme
import com.kevus.necessary.viemodels.BirthdayViewModel
import com.kevus.necessary.viemodels.DataStoreViewModel
import com.kevus.necessary.viemodels.TaskViewModel
import com.kevus.necessary.widgets.BirthdayCards
import com.kevus.necessary.widgets.BottomNavBar
import java.util.*

@Composable
fun BirthdayReminderScreen(navController: NavController, taskViewModel: TaskViewModel, dataStoreViewModel: DataStoreViewModel, birthdayViewModel: BirthdayViewModel) {

    NecessaryTheme(darkTheme = true, dataStoreViewModel = dataStoreViewModel) {
        Scaffold(
//            backgroundColor = Color.DarkGray,
            topBar = {
                TopAppBar(){
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "BIRTHDAYS",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            style = MaterialTheme.typography.body1,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
            },
            bottomBar = {
                BottomNavBar(navController = navController, dataStoreViewModel = dataStoreViewModel)
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                MainContent(navController = navController, birthdayViewModel = birthdayViewModel)
            }
        }
    }
}

@Composable
private fun MainContent(navController: NavController, birthdayViewModel: BirthdayViewModel){

    var name by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf("") }
    var search by remember { mutableStateOf("") }

    Column {
//        AddBirthdayWidget { birthday ->
//            birthdayViewModel.addBirthday(birthday = birthday)
//        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Add a Birthday!", modifier = Modifier.align(Alignment.CenterHorizontally))




            Row {
                OutlinedTextField(
                    modifier = Modifier.weight(0.9f),
                    value = name,
                    onValueChange = { value -> name = value },
                    label = { Text(text = "Name", style = MaterialTheme.typography.subtitle1) },
                )
                OutlinedTextField(
                    modifier = Modifier.weight(1.1f),
                    value = birthday,
                    onValueChange = { value -> birthday = value },
                    label = { Text(text = "Birthday", style = MaterialTheme.typography.subtitle1) },
                    placeholder = { Text(text = "14.03.1998") }
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(
                    modifier = Modifier.padding(16.dp),
                    onClick = {
                        if (name.isNotEmpty() and birthday.isNotEmpty()) {
                            val dateArr = birthday.split('.')
                            val day = dateArr[0].toInt()
                            val month = dateArr[1].toInt()
                            val year = dateArr[2].toInt()

                            val calendar = Calendar.getInstance()
                            calendar.set(year, month, day)

                            val newBirthday = Birthday(
                                Name = name,
                                Birthday = calendar.timeInMillis
                            )
                            birthdayViewModel.addBirthday(newBirthday)
                        }
                        name = ""
                        birthday = ""
                    }) {
                    Text(text = "Add")
                }
                OutlinedTextField(
                    modifier = Modifier.weight(1.1f),
                    value = search,
                    onValueChange = { value -> search = value },
                    label = { Text(text = "Search", style = MaterialTheme.typography.subtitle1) },
                    placeholder = { Text(text = "Search") }
                )
            }
        }

        val loadedBirthdays: List<Birthday> by birthdayViewModel.birthdays.collectAsState()
        var testBD : List<Birthday> = loadedBirthdays
        Divider()
        if(search.isNotEmpty()) {
            val searchedBirthdays = loadedBirthdays.filter { it.Name.contains(search) }
            testBD = searchedBirthdays
        }

        BirthdayCards(
            birthdays = testBD
        ) { birthday ->
            birthdayViewModel.removeBirthday(birthday = birthday)
        }
    }
}





