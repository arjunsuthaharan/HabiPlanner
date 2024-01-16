package com.example.habit_tracker.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditHabitScreen(
    id : Int,
    state: HabitState,
    navController: NavController,
    onEvent: (HabitEvents) -> Unit
){
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(HabitEvents.UpdateHabit(
                    habitTitle = state.habitTitle.value,
                    habitDescription = state.habitDescription.value
                ))
                navController.popBackStack()
            }) {

                Icon(imageVector = Icons.Rounded.Check, contentDescription = "Save Note")
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
        }

    }
}