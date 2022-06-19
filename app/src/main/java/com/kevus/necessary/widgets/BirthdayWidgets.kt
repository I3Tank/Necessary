package com.kevus.necessary.widgets

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kevus.necessary.models.Birthday
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun BirthdayBox(
    birthday: Birthday,
    onItemClick: (Birthday) -> Unit = {}
) {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = birthday.Birthday
    val mDay = calendar.get(Calendar.DAY_OF_MONTH)
    val mMonth = calendar.get(Calendar.MONTH)
    val mYear = calendar.get(Calendar.YEAR)
    val birthDate = "${mDay}.${mMonth}.${mYear}"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(2.5.dp),
        shape = RoundedCornerShape(corner = CornerSize(1.dp)),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(5.dp)
        ) {
            //Text for the Name
            Text(
                text = birthday.Name,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.body2
            )
            //Text for the Date
            Text(
                text = birthDate,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.subtitle2
            )
            IconButton(onClick = { onItemClick(birthday) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Birthday"
                )
            }
        }
    }
}

@Composable
fun BirthdayCards(
    birthdays: List<Birthday> = listOf(),
    onDeleteClick: (Birthday) -> Unit = {}
){
    LazyColumn() {
        items(birthdays) { birthday ->
            BirthdayBox(
                birthday = birthday,
                onItemClick = { birthdayToRemove ->
                    onDeleteClick(birthdayToRemove)
                })
        }
    }
}