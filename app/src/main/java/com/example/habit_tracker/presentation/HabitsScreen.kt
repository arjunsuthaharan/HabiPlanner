package com.example.habit_tracker.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.Sort
import androidx.compose.material.icons.rounded.SyncLock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitsScreen(
    state: HabitState,
    navController: NavController,
    onEvent: (HabitEvents) -> Unit
){

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(15.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(text = "HabiPlanner",
                    modifier = Modifier
                        .weight(1f),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                IconButton(onClick = {onEvent(HabitEvents.SortHabits)}) {
                    Icon(imageVector = Icons.Rounded.Sort,
                        contentDescription = "Sort Habits",
                        modifier = Modifier.size(35.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }

            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                state.habitTitle.value = ""
                state.habitDescription.value = ""
                navController.navigate("AddHabitScreen")
            }) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add New Habit")
            }
        }
    ){ paddingValues ->

        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ){
            items(state.habits.size) { index ->
                HabitItem(state = state, index = index, navController = navController, onEvent = onEvent)
            }
        }


    }

}

@Composable
fun HabitItem(
    state: HabitState,
    index: Int,
    navController: NavController,
    onEvent: (HabitEvents) -> Unit,
){

    var showDialog by remember { mutableStateOf(false) }

    if(showDialog){
        AlertDialog(
            icon = {
                Icon(Icons.Default.Info, contentDescription = "Example Icon")
            },
            title = {
                Text(text = "Reset Streak")
            },
            text = {
                Text(text = "Are you sure you want to reset your streak?")
            },
            onDismissRequest = {
                showDialog = false
            },
            confirmButton = {
                Button(
                    onClick = { resetStreak(index = index, state = state, onEvent = onEvent)
                    showDialog = false}
                    //onClick = {showDialog = false}
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = {showDialog = false}
                ) {
                    Text("No")
                }
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(15.dp)
    ){
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = state.habits[index].habitTitle,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = state.habits[index].habitDescription,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Started : " + convertLongToTime(state.habits[index].habitStartDate),
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Streak : " + getHabitStreak(state.habits[index].habitStartDate) + " day(s)",
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        IconButton(onClick = {
            showDialog = true
        }) {
            Icon(
                imageVector = Icons.Rounded.SyncLock, contentDescription = "Reset Streak",
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        IconButton(onClick = {
            state.habitID.value = state.habits[index].habitID
            state.habitStartDate.value = state.habits[index].habitStartDate
            state.habitTitle.value = state.habits[index].habitTitle
            state.habitDescription.value = state.habits[index].habitDescription
            navController.navigate("EditHabitScreen")
        }) {
            Icon(
                imageVector = Icons.Rounded.EditNote, contentDescription = "Update Habit",
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

            IconButton(onClick = { onEvent(HabitEvents.DeleteHabit(state.habits[index]))
            }) {
                Icon(imageVector = Icons.Rounded.Delete, contentDescription = "Delete Habit",
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer)
            }
    }
}

fun resetStreak(
    index : Int,
    state: HabitState,
    onEvent: (HabitEvents) -> Unit
){
    state.habitID.value = state.habits[index].habitID
    state.habitStartDate.value = System.currentTimeMillis()
    state.habitTitle.value = state.habits[index].habitTitle
    state.habitDescription.value = state.habits[index].habitDescription
    onEvent(HabitEvents.UpdateHabit(
        habitID = state.habitID.value,
        habitTitle = state.habitTitle.value,
        habitDescription = state.habitDescription.value,
        habitStartDate = state.habitStartDate.value
    ))
}

fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("MM.dd.yyyy")
    return format.format(date)
}

fun getHabitStreak(time: Long): String {
    val streak = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - time)
    return streak.toString()
}