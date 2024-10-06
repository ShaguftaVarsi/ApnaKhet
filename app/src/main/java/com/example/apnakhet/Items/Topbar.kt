package com.example.apnakhet.Items

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {
    var menuExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = "ApnaKhet",
                modifier = Modifier.clickable {
                    // Navigate to HomePage when app name is clicked
                    navController.navigate("homePage")
                },
                style = MaterialTheme.typography.titleLarge.copy(
                    textDecoration = TextDecoration.None
                )
            )
        },
        actions = {
            // Icon for menu
            IconButton(onClick = { menuExpanded = !menuExpanded }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Menu")
            }

            // Dropdown menu
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("My Account") },
                    onClick = {
                        menuExpanded = false
                        // Navigate to My Account page
                        navController.navigate("myAccount")
                    }
                )
                DropdownMenuItem(
                    text = { Text("Settings") },
                    onClick = {
                        menuExpanded = false
                        // Navigate to Settings page
                        navController.navigate("settings")
                    }
                )
            }
        }
    )
}