package com.example.apnakhet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material.icons.*
import androidx.compose.ui.graphics.Color


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun cultivationTips(navController: NavController) {
    var selectedCrop by remember { mutableStateOf("Sugarcane") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cultivation Tips") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Dropdown to select crop type
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text("See relevant information on ", fontSize = 16.sp)
                DropdownMenuCropSelector(selectedCrop) { crop ->
                    selectedCrop = crop
                }
            }

            // Toggle between "By Task" and "By Stage"
            var selectedTab by remember { mutableStateOf(0) }
            TabRow(
                selectedTabIndex = selectedTab,
                modifier = Modifier.fillMaxWidth()
            ) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) {
                    Text("By Task", modifier = Modifier.padding(16.dp))
                }
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                    Text("By Stage", modifier = Modifier.padding(16.dp))
                }
            }

            // Display task list (like the image)
            if (selectedTab == 0) {
                CultivationTaskList(navController)
            } else {
                // Implement Stage-based tasks here if needed
            }
        }
    }
}

@Composable
fun DropdownMenuCropSelector(selectedCrop: String, onCropSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val crops = listOf("Sugarcane", "Wheat", "Rice", "Corn")

    Box(
        modifier = Modifier
            .background(Color.LightGray, RoundedCornerShape(8.dp))
            .clickable { expanded = true }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text = selectedCrop)
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            crops.forEach { crop ->
                DropdownMenuItem(onClick = {
                    onCropSelected(crop)
                    expanded = false
                }) {
                    Text(text = crop)
                }
            }
        }
    }
}

fun DropdownMenuItem(onClick: () -> Unit, interactionSource: @Composable () -> Unit) {
//    TODO("Not yet implemented")
}

@Composable
fun CultivationTaskList(navController: NavController) {
    // List of tasks with distinct icons and routes
    val tasks = listOf(
        TaskData("Plant Selection", Icons.Default.Search, "plant_selection_route"),
        TaskData("Planting", Icons.Default.Place, "planting_route"),
        TaskData("Plant Training", Icons.Default.Build, "plant_training_route"),
        TaskData("Monitoring", Icons.Default.Edit, "monitoring_route"),
        TaskData("Site Selection", Icons.Default.Place, "site_selection_route"),
        TaskData("Field Preparation", Icons.Default.Settings, "field_preparation_route"),
        TaskData("Weeding", Icons.Default.Warning, "weeding_route"),
        TaskData("Irrigation", Icons.Default.ThumbUp, "irrigation_route"),
        TaskData("Fertilization Organic", Icons.Default.Star, "fertilization_organic_route")
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        tasks.forEach { task ->
            TaskRow(task, navController)
            Divider()
        }
    }
}

// Data class to hold task information
data class TaskData(
    val name: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun TaskRow(task: TaskData, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable {
                // Navigate to the specific route when the row is clicked
                navController.navigate(task.route)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = task.icon,
            contentDescription = task.name,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(task.name, fontSize = 18.sp)
    }
}
