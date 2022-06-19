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
fun AddBirthdayWidget(
    onAddClick: (Birthday) -> Unit = {}
){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Add a Birthday!", modifier = Modifier.align(Alignment.CenterHorizontally))

        var name by remember { mutableStateOf("") }
        var birthday by remember { mutableStateOf("") }
        var search by remember { mutableStateOf("") }


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
                        onAddClick(newBirthday)
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