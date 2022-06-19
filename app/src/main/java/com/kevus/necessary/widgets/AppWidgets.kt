package com.kevus.necessary.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kevus.necessary.navigation.AppScreens
import com.kevus.necessary.viemodels.DataStoreViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.*

@Composable
fun TopDayBar(
    onToggleClick: (Boolean) -> Unit = {}
) {
    val sdf = SimpleDateFormat("dd.M")
    val currentDate = sdf.format(Date())
    val currentDay = LocalDate.now().dayOfWeek.toString() + "  " + currentDate

    //FIXME: small box making text not centered
    TopAppBar(
        title = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = currentDay,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable { onToggleClick(false) },
                    style = MaterialTheme.typography.body1,
                )
            }
        }
    )
}
@Composable
fun TopWeekBar(
    onToggleClick: (Boolean) -> Unit = {}
) {
    val currentDate = LocalDate.now()
    val test = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()
    val weekNumber = currentDate.get(test)

    val configuration = LocalConfiguration.current
    var screenWidth = configuration.screenWidthDp.dp / 7

    TopAppBar(
        title = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Week $weekNumber",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable { onToggleClick(true) },
                    style = MaterialTheme.typography.body1,
                )
                Row(){
                    Text(text = "MON", Modifier.width(screenWidth), style = MaterialTheme.typography.subtitle2)
                    Text(text = "TUE", Modifier.width(screenWidth), style = MaterialTheme.typography.subtitle2)
                    Text(text = "WED", Modifier.width(screenWidth), style = MaterialTheme.typography.subtitle2)
                    Text(text = "THU", Modifier.width(screenWidth), style = MaterialTheme.typography.subtitle2)
                    Text(text = "FRI", Modifier.width(screenWidth), style = MaterialTheme.typography.subtitle2)
                    Text(text = "SAT", Modifier.width(screenWidth), style = MaterialTheme.typography.subtitle2)
                    Text(text = "SUN", Modifier.width(screenWidth), style = MaterialTheme.typography.subtitle2)
                }
            }
        }
    )
}



@Composable
fun SimpleTopAppBar(arrowBackClicked: () -> Unit = {}, content: @Composable () -> Unit){
    TopAppBar(elevation = 3.dp) {
        Row {
            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = "Arrow back",
                modifier = Modifier.clickable {
                    arrowBackClicked()
                }
            )

            Spacer(modifier = Modifier.width(20.dp))

            content()
        }
    }
}

@Composable
fun BottomNavBar(navController: NavController, dataStoreViewModel: DataStoreViewModel) {
    val getBirthdayTabActive = dataStoreViewModel.birthdayTabActive.observeAsState().value
    BottomAppBar(elevation = 3.dp) {
        Row {
            IconButton(modifier = Modifier.weight(1f), onClick = {
                navController.navigate(AppScreens.SettingsScreen.name)
            }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Open Settings Screen"
                )
            }
            IconButton(modifier = Modifier.weight(1f), onClick = {
                navController.navigate(AppScreens.OverviewScreen.name)
            }) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Open Daily/Weekly Screen"
                )
            }
            IconButton(modifier = Modifier.weight(1f), onClick = {
                navController.navigate(AppScreens.TaskListScreen.name)
            }) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Open Task List Screen"
                )
            }
            if(getBirthdayTabActive == true){
                IconButton(modifier = Modifier.weight(1f), onClick = {
                    navController.navigate(AppScreens.BirthdayReminderScreen.name)
                }) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Open Birthday Reminder Screen"
                    )
                }
            }
        }
    }
}
