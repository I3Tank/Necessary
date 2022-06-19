@file:Suppress("DEPRECATION")

package com.kevus.necessary.models


import androidx.compose.runtime.Composable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Birthdays")
data class Birthday(
    @PrimaryKey()
    val Name: String,
    val Birthday: Long,
)
private var calendar: Calendar = Calendar.getInstance()
@Composable
fun getTestBirthday(): Birthday {
    calendar.set(1998, 3, 14)
    return Birthday(
        Name = "Marcus",
        Birthday = calendar.timeInMillis
    )
}