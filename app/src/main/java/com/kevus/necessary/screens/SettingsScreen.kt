package com.kevus.necessary.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kevus.necessary.navigation.AppScreens
import com.kevus.necessary.ui.theme.NecessaryTheme
import com.kevus.necessary.viemodels.DataStoreViewModel
import com.kevus.necessary.widgets.BottomNavBar
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(navController: NavController, dataStoreViewModel: DataStoreViewModel) {

    NecessaryTheme(darkTheme = true, dataStoreViewModel = dataStoreViewModel) {
        Scaffold(
            topBar = {
                TopAppBar(elevation = 3.dp) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "SETTINGS",
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
        ) {innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                MainContent(dataStoreViewModel, navController)
            }
        }
    }
}

@Composable
private fun MainContent(dataStoreViewModel: DataStoreViewModel, navController: NavController){
    val scrollState = rememberScrollState()
    Column(Modifier.verticalScroll(scrollState)) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Text(text = "Login", fontSize = 20.sp)
                Row() {
                    Button(onClick = {
                        navController.navigate(AppScreens.SignInScreen.name)
                    }, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "G+ Login")
                    }
                }
            }
        }
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "CONFIGURE TABS", style = MaterialTheme.typography.body2, fontSize = 25.sp)
                Row() {
                    Text(text = "Birthday Reminder", style = MaterialTheme.typography.subtitle2)
                    ScreenCheckBox(dataStoreViewModel = dataStoreViewModel)

                }
            }
        }
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "THEMES", style = MaterialTheme.typography.body2, fontSize = 25.sp)
                Row() {
                    ThemeRadioButtons(dataStoreViewModel = dataStoreViewModel)
                }
            }
        }
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "STARTUP SCREEN", style = MaterialTheme.typography.body2, fontSize = 25.sp)
                Row() {
                    StartupRadioButtons(dataStoreViewModel = dataStoreViewModel)
                }
            }
        }
    }
}

@Composable
private fun ScreenCheckBox(dataStoreViewModel: DataStoreViewModel){
    val getBirthdayTabActive = dataStoreViewModel.birthdayTabActive.observeAsState().value
    val checkedState = remember {mutableStateOf(getBirthdayTabActive)}
    val scope = rememberCoroutineScope()
    Checkbox(
        checked = checkedState.value!!,
        onCheckedChange = {
            checkedState.value = it
            //Here we save the settings for the BirthdayScreen
            scope.launch {
                dataStoreViewModel.setBirthdayScreenTab(checkedState.value!!)
            }
        }
    )
}

@Composable
fun ThemeRadioButtons(dataStoreViewModel: DataStoreViewModel) {
    val activeTheme = dataStoreViewModel.activeTheme.observeAsState().value
    val scope = rememberCoroutineScope()


    val radioOptions = listOf("Dark", "Light", "Red", "Terra Cotta")
    val index = radioOptions.indexOf(activeTheme)
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[index]) }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = {
                                onOptionSelected(text)
                                //we have this in total of 2 times, since we can click the radio button AND the Card ??
                                scope.launch {
                                    dataStoreViewModel.setActiveTheme(text)
                                }
                            }
                        )
                        .padding(horizontal = 16.dp)
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        modifier = Modifier.padding(all = Dp(value = 8F)),
                        onClick = {
                            onOptionSelected(text)
                            scope.launch {
                                dataStoreViewModel.setActiveTheme(text)
                            }
                        }
                    )
                    Text(
                        text = text,
                        modifier = Modifier.padding(start = 16.dp),
                        style = MaterialTheme.typography.body2
                    )

                }
            }
        }
    }
}

@Composable
fun StartupRadioButtons(dataStoreViewModel: DataStoreViewModel) {
    val startupScreen = dataStoreViewModel.startupScreen.observeAsState().value
    val scope = rememberCoroutineScope()


    val radioOptions = listOf("Daily View", "Tasklist View", "Birthday View")
    val index = radioOptions.indexOf(
        when(startupScreen){
            AppScreens.OverviewScreen.name -> "Daily View"
            AppScreens.TaskListScreen.name -> "Tasklist View"
            AppScreens.BirthdayReminderScreen.name -> "Birthday View"
            else -> "Daily View"
        }
    )
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[index]) }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = {
                                onOptionSelected(text)
                                //we have this in total of 2 times, since we can click the radio button AND the Card ??
                                scope.launch {
                                    when(text){
                                        "Daily View" -> dataStoreViewModel.setStartupScreen(AppScreens.OverviewScreen.name)
                                        "Tasklist View" -> dataStoreViewModel.setStartupScreen(AppScreens.TaskListScreen.name)
                                        "Birthday View" -> dataStoreViewModel.setStartupScreen(AppScreens.BirthdayReminderScreen.name)
                                    }
                                }
                            }
                        )
                        .padding(horizontal = 16.dp)
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        modifier = Modifier.padding(all = Dp(value = 8F)),
                        onClick = {
                            onOptionSelected(text)
                            scope.launch {
                                when(text){
                                    "Daily View" -> dataStoreViewModel.setStartupScreen(AppScreens.OverviewScreen.name)
                                    "Tasklist View" -> dataStoreViewModel.setStartupScreen(AppScreens.TaskListScreen.name)
                                    "Birthday View" -> dataStoreViewModel.setStartupScreen(AppScreens.BirthdayReminderScreen.name)
                                }
                            }
                        }
                    )
                    Text(
                        text = text,
                        modifier = Modifier.padding(start = 16.dp),
                        style = MaterialTheme.typography.body2
                    )

                }
            }
        }
    }
}