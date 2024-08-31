package com.example.habit_tracker.presentation

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
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Sort
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHabitScreen(
    state: HabitState,
    navController: NavController,
    onEvent: (HabitEvents) -> Unit
){
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
            Text(text = "Add New Habit",
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


            Button(onClick = {
                onEvent(HabitEvents.SaveHabit(
                    habitTitle = state.habitTitle.value,
                    habitDescription = state.habitDescription.value
                ))
                navController.popBackStack()
            },                modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),) {

                Text("Save Habit")
            }
        }

    }
}