package com.kevus.necessary.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Checkbox
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kevus.necessary.viemodels.DataStoreViewModel
import com.kevus.necessary.viemodels.TaskViewModel
import com.kevus.necessary.widgets.BottomNavBar
import com.kevus.necessary.widgets.SimpleTopAppBar
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(navController: NavController, dataStoreViewModel: DataStoreViewModel){
    Scaffold(
        backgroundColor = Color.DarkGray,
        topBar = {
            TopAppBar(elevation = 3.dp) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Settings",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                    )
                }
            }
        },
        bottomBar = {
            BottomNavBar(navController = navController, dataStoreViewModel = dataStoreViewModel)
        }
    ) {
        MainContent(dataStoreViewModel)
    }
}

@Composable
private fun MainContent(dataStoreViewModel: DataStoreViewModel){
    Column() {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column() {
                Text(text = "Login", fontSize = 20.sp)
                Row() {
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "G+ Login")
                    }
                }
            }
        }
        Card(modifier = Modifier.fillMaxWidth()) {
            Column() {
                Text(text = "Configure Tabs", fontSize = 20.sp)
                Row() {
                    Text(text = "Birthday Reminder")
                    ScreenCheckBox(dataStoreViewModel = dataStoreViewModel)

                }
            }
        }
        Card(modifier = Modifier.fillMaxWidth()) {
            Column() {
                Text(text = "Current Theme", fontSize = 20.sp)
                Row {
                    SimpleRadioButtonComponent(dataStoreViewModel = dataStoreViewModel)
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
fun SimpleRadioButtonComponent(dataStoreViewModel: DataStoreViewModel) {
    val activeTheme = dataStoreViewModel.activeTheme.observeAsState().value
    val scope = rememberCoroutineScope()


    val radioOptions = listOf("Dark", "Light", "Color")
    val index = radioOptions.indexOf(activeTheme)
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[index]) }
    Column(
        // we are using column to align our
        // imageview to center of the screen.
//        modifier = Modifier
//            .fillMaxWidth()
//            .fillMaxHeight(),

        // below line is used for
        // specifying vertical arrangement.
        verticalArrangement = Arrangement.Center,

        // below line is used for
        // specifying horizontal arrangement.
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // we are displaying all our
        // radio buttons in column.
        Column {
            // below line is use to set data to
            // each radio button in columns.
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        // using modifier to add max
                        // width to our radio button.
                        .fillMaxWidth()
                        // below method is use to add
                        // selectable to our radio button.
                        .selectable(
                            // this method is called when
                            // radio button is selected.
                            selected = (text == selectedOption),
                            // below method is called on
                            // clicking of radio button.
                            onClick = {
                                onOptionSelected(text)
                                //we have this in total of 2 times, since we can click the radio button AND the Card ??
                                scope.launch {
                                    dataStoreViewModel.setActiveTheme(text)
                                }
                            }
                        )
                        // below line is use to add
                        // padding to radio button.
                        .padding(horizontal = 16.dp)
                ) {
                    // below line is use to get context.
                    val context = LocalContext.current

                    // below line is use to add
                    // text to our radio buttons.
                    Text(
                        text = text,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    // below line is use to
                    // generate radio button
                    RadioButton(
                        // inside this method we are
                        // adding selected with a option.
                        selected = (text == selectedOption),modifier = Modifier.padding(all = Dp(value = 8F)),
                        onClick = {
                            // inside on click method we are setting a
                            // selected option of our radio buttons.
                            onOptionSelected(text)
                            scope.launch {
                                dataStoreViewModel.setActiveTheme(text)
                            }
                            // after clicking a radio button
                            // we are displaying a toast message.
                            // Toast.makeText(context, text, Toast.LENGTH_LONG).show()
                        }
                    )

                }
            }
        }
        Text(text = "Active theme = $activeTheme")
    }
}