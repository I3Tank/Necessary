package com.kevus.necessary.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kevus.necessary.navigation.AppScreens
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

@Composable
fun TopDateBar() {
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
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
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
fun BottomNavBar(navController: NavController) {
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
        }
    }
}
