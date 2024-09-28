package com.asjdev.habit_tracker.presentation

import android.os.Handler
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Sort
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit


// Function for displaying the HabitsScreen
// Retrieves state, navController, and onEvent handler
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitsScreen(
    state: HabitState,
    navController: NavController,
    onEvent: (HabitEvents) -> Unit
){
    val context = LocalContext.current
    var sortMessage by remember { mutableStateOf("Sorted by date") }

    // Scaffold for the top header holding the app title and sort button, and users habits pulled from database
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

                //Icon Button for sorting habits by date or title, calls SortHabits() function from HabitEvents
                IconButton(onClick = {

                    println(sortMessage)

                    sortMessage = if(sortMessage == "Sorted by date"){
                        "Sorted by title"
                    } else{
                        "Sorted by date"
                    }

                    println(sortMessage)

                    val toast = Toast.makeText(context, sortMessage, Toast.LENGTH_SHORT)
                    toast.show()

                    Handler().postDelayed({
                        toast.cancel()
                    }, 1000)


                    onEvent(HabitEvents.SortHabits)}) {
                    Icon(imageVector = Icons.Rounded.Sort,
                        contentDescription = "Sort Habits",
                        modifier = Modifier.size(35.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }

                /*
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More"
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Sort by Name/Date") },
                        onClick = { onEvent(HabitEvents.SortHabits)}
                    )
               }

                 */
            }
        },
        // FloatingActionButton on bottom right of display to navigate users to AddHabitsScreen
        floatingActionButton = {
            FloatingActionButton(onClick = {
                state.habitTitle.value = ""
                state.habitDescription.value = ""
                navController.navigate("AddHabitScreen")
            }, containerColor = MaterialTheme.colorScheme.primary){
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add New Habit",
                    tint = MaterialTheme.colorScheme.onPrimary)

            }
        }

    ){ paddingValues ->

        // LazyColumn to populate with HabitItems from database
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

    // Message with instructions displayed to user if no habits exist in databases
    if(state.habits.isEmpty()){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            BasicText(text = "To get started, press the '+' icon on the bottom right to create your first habit!",
                style = TextStyle(
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSecondary
            )
            )
        }
    }


}

// HabitItem function for displaying and handling individual habits
// Retreives the state, index, navController and onEvent
@Composable
fun HabitItem(
    state: HabitState,
    index: Int,
    navController: NavController,
    onEvent: (HabitEvents) -> Unit,
){

    val context = LocalContext.current

    var showResetDialog by remember { mutableStateOf(false) }

    // AlertDialog for confirmation if user wants to reset the streak of the selected habit
    // On confirmation calls resetStreak function passing index state and onEvent
    if(showResetDialog){
        AlertDialog(
            title = {
                Text(text = "Reset Streak")
            },
            text = {
                Text(text = "Are you sure you want to reset your streak?")
            },
            onDismissRequest = {
                showResetDialog = false
            },
            confirmButton = {
                Button(
                    onClick = { resetStreak(index = index, state = state, onEvent = onEvent)
                    showResetDialog = false}
                    //onClick = {showDialog = false}
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = {showResetDialog = false}
                ) {
                    Text("No")
                }
            }
        )
    }

    var showDeleteDialog by remember { mutableStateOf(false) }

    // AlertDialog for confirmation if user wants to delete the selected habit
    // On confirm DeleteHabit function is called from HabitEvents, passing the habits index
    if(showDeleteDialog){
        AlertDialog(
            title = {
                Text(text = "Delete Habit")
            },
            text = {
                Text(text = "Are you sure you want to delete this habit?")
            },
            onDismissRequest = {
                showDeleteDialog = false
            },
            confirmButton = {
                Button(
                    onClick = { onEvent(HabitEvents.DeleteHabit(state.habits[index]))
                        showDeleteDialog = false
                        val toast = Toast.makeText(context, "Deleted Habit", Toast.LENGTH_SHORT)
                        toast.show()}
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = {showDeleteDialog = false}
                ) {
                    Text("No")
                }
            }
        )
    }

    // Row for displaying each habit
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.secondary)
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

        // Icon Button for navigating user to edit habit, while passing the index and state of the habit

        IconButton(onClick = {
            editHabit(index = index, state = state, navController = navController)
        }) {
            Icon(
                imageVector = Icons.Rounded.EditNote, contentDescription = "Update Habit",
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        // Icon Button for resetting streak
        // On click triggers the AlertDialog for user confirmation
        IconButton(onClick = {
            showResetDialog = true
        }) {
            Icon(
                imageVector = Icons.Rounded.Refresh, contentDescription = "Reset Streak",
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        // Icon Button for deleting streak
        // On click triggers the AlertDialog for user confirmation
        IconButton(
            onClick = { showDeleteDialog = true
        }) {
            Icon(imageVector = Icons.Rounded.Delete, contentDescription = "Delete Habit",
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

// Function for editing existing habit
// Retrieves the habits index and state, setting the habit variables to current values before navigating to EditHabitScreen
fun editHabit(
    index : Int,
    state: HabitState,
    navController: NavController,
){
    state.habitID.value = state.habits[index].habitID
    state.habitStartDate.value = state.habits[index].habitStartDate
    state.habitTitle.value = state.habits[index].habitTitle
    state.habitDescription.value = state.habits[index].habitDescription
    navController.navigate("EditHabitScreen")
}

// Function for resetting habit streak by resetting habitStartDate value
// Retrieves the habits index and state, setting all habit variables to current values except habitStartDate which is set to current time
// Calls UpdateHabit function from HabitEvents directly to update values to database
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


// Function for convertingLong date time to MM.dd.yyyy format
fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("MM.dd.yyyy")
    return format.format(date)
}

// Function for retrieving the habit streak, by subtracting current time by habitStartDate time and converting to days
fun getHabitStreak(time: Long): String {
    val streak = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - time)
    return streak.toString()
}