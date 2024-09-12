package com.example.apnakhet.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.apnakhet.AuthState
import com.example.apnakhet.AuthViewModel
import com.example.apnakhet.R

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {

    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }

    Column(
        modifier.fillMaxSize(), Arrangement.Top, Alignment.CenterHorizontally
    ) {
        // Weather and location
        WeatherSection()

        Spacer(modifier = Modifier.height(16.dp))

        // Healing options
        HealingOptionsSection()

        Spacer(modifier = Modifier.height(24.dp))

        // Other options like Fertilizer, Pests & diseases, Cultivation, etc.
        OptionsGridSection()

        Spacer(modifier = Modifier.weight(1f))

        // Logout Button
        TextButton(onClick = {
            authViewModel.signout()
        }) {
            Text(text = "Sign out", color = Color.Gray)
        }
    }
}

@Composable
fun WeatherSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Location and weather info { /* TODO: Implement Weather API */ }

        Column {
            Text(text = "Shivkar, 12 Sep", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = "Rain - 25°C / 29°C", fontSize = 14.sp)
        }

        // Weather icon and spraying status
        Column(horizontalAlignment = Alignment.End) {
            Text(text = "29°C", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = "Unfavorable spraying condition",
                    tint = Color.Red
                )
                Text(text = "Unfavorable", fontSize = 14.sp, color = Color.Red)
            }
        }
    }
}

@Composable
fun HealingOptionsSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFE0F7FA), RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Heal your crop", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_multiline_chart_black_24dp), //take picture wala pic heal your crop ke niche
                        contentDescription = "Take a picture",
                        modifier = Modifier.size(48.dp)
                    )
                    Text(text = "Take a picture", fontSize = 12.sp)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.handwash), // mobile ka icon
                        contentDescription = "See diagnosis",
                        modifier = Modifier.size(48.dp)
                    )
                    Text(text = "See diagnosis", fontSize = 12.sp)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.handwash), // bottle ka icon
                        contentDescription = "Get medicine",
                        modifier = Modifier.size(48.dp)
                    )
                    Text(text = "Get medicine", fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { /* TODO: Implement camera action */ }) {
                Text(text = "Take a picture")
            }
        }
    }
}

@Composable
fun OptionsGridSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OptionCard(iconRes = R.drawable.handwash, title = "Fertilizer calculator") // fertilizer ka icon
            OptionCard(iconRes = R.drawable.handwash, title = "Pests & diseases") // pest ka icon
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OptionCard(iconRes = R.drawable.handwash, title = "Cultivation Tips")  //cultivation ka icon
            OptionCard(iconRes = R.drawable.handwash, title = "Pests and Disease Alerts") // warning icon
        }
    }
}

@Composable
fun OptionCard(iconRes: Int, title: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .size(160.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                modifier = Modifier.size(48.dp)
            )
            Text(text = title, fontSize = 14.sp)
        }
    }
}

