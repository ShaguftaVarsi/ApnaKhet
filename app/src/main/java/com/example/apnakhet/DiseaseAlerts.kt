//package com.example.apnakhet
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.BasicTextField
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.Warning
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import coil.compose.AsyncImage
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun diseaseAlerts(navController: NavController) {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Pests and Disease Alert") },
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
//                    }
//                }
//            )
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .padding(16.dp)
//        ) {
//            Text(
//                text = "High risk pests and diseases that might appear in your field, updated daily.",
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Medium,
//                modifier = Modifier.padding(bottom = 16.dp)
//            )
//
//            // List of plants with their pests
//            PestDiseaseAlert("Cabbage", "Tobacco Caterpillar", R.drawable.cabbage_image)
//            Spacer(modifier = Modifier.height(16.dp))
//            PestDiseaseAlert("Tomato", "Bacterial Spot and Speck of Tomato", R.drawable.tomato_bacteria_image)
//            Spacer(modifier = Modifier.height(16.dp))
//            PestDiseaseAlert("Tomato", "Tobacco Caterpillar", R.drawable.tomato_caterpillar_image)
//            Spacer(modifier = Modifier.height(16.dp))
//            PestDiseaseAlert("Capsicum & Chilli", "Bacterial Leaf Spot", R.drawable.chilli_leaf_spot_image)
//        }
//    }
//}
//
//@Composable
//fun PestDiseaseAlert(plantName: String, pestName: String, imageResource: Int) {
//    Column(
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            Box(
//                modifier = Modifier
//                    .size(40.dp)
//                    .clip(CircleShape)
//                    .background(Color.LightGray)
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_plant), // Example plant icon
//                    contentDescription = plantName,
//                    modifier = Modifier.align(Alignment.Center)
//                )
//            }
//            Spacer(modifier = Modifier.width(8.dp))
//            Text(text = plantName, fontWeight = FontWeight.Bold, fontSize = 18.sp)
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .clip(RoundedCornerShape(8.dp))
//                .background(Color.White)
//                .padding(8.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Box(
//                modifier = Modifier
//                    .clip(RoundedCornerShape(8.dp))
//                    .background(Color(0xFFFFEAEA))
//                    .padding(8.dp)
//            ) {
//                Text(
//                    text = "Alert",
//                    fontSize = 14.sp,
//                    color = Color.Red,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//
//            Spacer(modifier = Modifier.width(8.dp))
//
//            // Image of the pest or disease
//            AsyncImage(
//                model = imageResource,
//                contentDescription = pestName,
//                modifier = Modifier
//                    .size(80.dp)
//                    .clip(RoundedCornerShape(8.dp)),
//                contentScale = ContentScale.Crop
//            )
//
//            Spacer(modifier = Modifier.width(16.dp))
//
//            // Pest/Disease name
//            Column {
//                Text(text = "Insect", color = Color.Gray, fontSize = 14.sp)
//                Text(text = pestName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
//            }
//        }
//    }
//}
