package com.asjdev.habit_tracker.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Function for displaying the Edit Habits Screen
// Retrieves state, navController and onEvent handler
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditHabitScreen(
    state: HabitState,
    navController: NavController,
    onEvent: (HabitEvents) -> Unit
){

    var inVal = true
    val context = LocalContext.current

    // Scaffold for the top header holding the app title and back navigation button, input fields and button for editing existing habit
    Scaffold(topBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(MaterialTheme.colorScheme.primary)
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            IconButton(onClick = {navController.popBackStack()}) {
                Icon(imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Sort Habits",
                    modifier = Modifier.size(35.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            Text(text = "Edit Habit",
                modifier = Modifier
                    .weight(1f),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

    ) {
            paddingValues ->

        Column(
            modifier = Modifier.padding(paddingValues)
                .fillMaxSize()
        ) {
            // TextFields for user to input habit title and description
            // TextFields will be populated with existing habits title and description values
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                value = state.habitTitle.value,
                onValueChange = {
                    state.habitTitle.value = it },
                textStyle = TextStyle(
                    fontWeight = FontWeight.SemiBold, fontSize = 17.sp
                ),
                placeholder = {
                    Text(text = "Title")
                }
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                value = state.habitDescription.value,
                onValueChange = {
                    state.habitDescription.value = it
                },
                placeholder = {
                    Text(text = "Description")
                }
            )

            // Button for editing habit
            // OnClick first performs input validation check to confirm habitTitle and habitDescription values are not null
            // If true retrieves user inputted values and calls UpdateHabit function from HabitEvents
            // If false displays toast message instructing user to populate both fields
            Button(onClick = {

                inVal = !(state.habitTitle.value == "" || state.habitDescription.value == "")

                if(inVal){
                    onEvent(HabitEvents.UpdateHabit(
                        habitID = state.habitID.value,
                        habitTitle = state.habitTitle.value,
                        habitDescription = state.habitDescription.value,
                        habitStartDate = state.habitStartDate.value
                    ))

                    val toast = Toast.makeText(context, "Updated Habit", Toast.LENGTH_SHORT)
                    toast.show()
                    navController.popBackStack()
                }

                else{
                    Toast.makeText(context, "Please enter the title and description of your existing habit.", Toast.LENGTH_SHORT).show()
                }

            },                modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),) {

                Text("Edit Habit")
            }
        }

    }
}