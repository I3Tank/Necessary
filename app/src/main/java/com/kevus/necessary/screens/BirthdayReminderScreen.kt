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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.kevus.necessary.models.Birthday
import com.kevus.necessary.ui.theme.NecessaryTheme
import com.kevus.necessary.viemodels.BirthdayViewModel
import com.kevus.necessary.viemodels.DataStoreViewModel
import com.kevus.necessary.viemodels.TaskViewModel
import com.kevus.necessary.widgets.AddBirthdayWidget
import com.kevus.necessary.widgets.BirthdayBox
import com.kevus.necessary.widgets.BirthdayCards
import com.kevus.necessary.widgets.BottomNavBar

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

    Column {
        AddBirthdayWidget { birthday ->
            birthdayViewModel.addBirthday(birthday = birthday)
        }

        Divider()

        val allBirthdays: List<Birthday> by birthdayViewModel.birthdays.collectAsState()

        BirthdayCards(
            birthdays = allBirthdays
        ) { birthday ->
            birthdayViewModel.removeBirthday(birthday = birthday)
        }
    }
}





