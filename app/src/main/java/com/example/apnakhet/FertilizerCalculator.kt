package com.example.apnakhet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun FertilizerCalculator(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Fertilizer Calculator",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        for (i in 1..3) {
            FertilizerInputBox(label = "Fertilizer $i")
            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(
            onClick = { /* Add your calculation logic here */ },
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text(text = "Calculate")
        }
    }
}

@Composable
fun FertilizerInputBox(label: String) {
    val textState = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.LightGray, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            modifier = Modifier.padding(16.dp),
            singleLine = true
        )
        if (textState.value.isEmpty()) {
            Text(text = label, color = Color.Gray)
        }
    }
}
