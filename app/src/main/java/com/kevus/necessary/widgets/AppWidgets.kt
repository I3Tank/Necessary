package com.kevus.necessary.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
fun SimpleTopAppBar(){
    TopAppBar(elevation = 3.dp) {
        Row {

        }
    }
}