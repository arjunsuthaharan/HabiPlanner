package com.example.habit_tracker.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun AlertDialog(
    onDismiss:()->Unit
){
    
    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = { /*TODO*/ },
        modifier = Modifier.height(350.dp),

        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(imageVector = Icons.Default.Info, contentDescription="Reset Streak")
                Text(text="Reset Streak",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },

        text = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Button(
                    onClick  = {},
                    modifier = Modifier
                        .width(180.dp)
                        .padding(10.dp)
                ){

                    Text(text = "Yes")

                }
                Button(
                    onClick  = {},
                    modifier = Modifier
                        .width(180.dp)
                        .padding(10.dp)
                ){

                    Text(text = "No")

                }
            }
        }


    )

    
}