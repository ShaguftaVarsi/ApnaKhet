package com.example.apnakhet.login
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.padding

import androidx.compose.runtime.*
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
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.FileProvider
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.apnakhet.Weather.WeatherViewModel
import com.example.apnakhet.youtube.YouTubeViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

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

        modifier = Modifier
            .verticalScroll(rememberScrollState()) // Add this line
            .padding(16.dp)
    ) {

        // Healing options
        HealingOptionsSection()

        Spacer(modifier = Modifier.height(24.dp))

        // Weather and location
        WeatherSection()

        Spacer(modifier = Modifier.height(16.dp))

        // Other options like Fertilizer, Pests & diseases, Cultivation, etc.
        OptionsGridSection(navController)

        Spacer(modifier = Modifier.weight(1f))

        YouTubeSection(navController)

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
    val viewModel: WeatherViewModel = viewModel()
    val weatherData by viewModel.weatherData.collectAsState()
//    val errorMessage = viewModel.errorMessage

    LaunchedEffect(Unit) {
        viewModel.fetchWeather(city = "Panvel", apiKey = "e46206c5656ba87110dc8909211896ca") // Add your actual API key here
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Location and weather info
        weatherData?.let { weather ->
            Column {
                Text(text = "${weather.name}, Today", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = "Rain - ${weather.main.temp_min}°C / ${weather.main.temp_max}°C", fontSize = 14.sp)
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(text = "${weather.main.temp}°C", fontSize = 24.sp, fontWeight = FontWeight.Bold)

                Text(
                    text = "Spraying Conditions",
                    fontSize = 14.sp
                )

                // Adjust condition based on weather (e.g., unfavorable spraying status)
                val unfavorableCondition = weather.main.temp > 28 // Example condition
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = if (unfavorableCondition) "Unfavorable spraying condition" else "Favorable condition",
                        tint = if (unfavorableCondition) Color.Red else Color.Green
                    )


                    Text(
                        text = if (unfavorableCondition) "Unfavorable" else "Favorable",
                        fontSize = 14.sp,
                        color = if (unfavorableCondition) Color.Red else Color.Green
                    )
                }
            }
        } ?: run {
            Text(text = "Error", color = Color.Red)
        }
    }
}



@Composable
fun HealingOptionsSection() {

    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".provider", file
    )

    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }


    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()){
            capturedImageUri = uri
        }


    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){
        if (it)
        {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        }
        else
        {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }




    if (capturedImageUri.path?.isNotEmpty() == true)
    {
        Image(
            modifier = Modifier
                .padding(16.dp, 8.dp),
            painter = rememberImagePainter(capturedImageUri),
            contentDescription = null
        )
    }
    else
    {
        Image(
            modifier = Modifier
                .padding(16.dp, 8.dp),
            painter = painterResource(id = R.drawable.noto_v1__bug),
            contentDescription = null
        )
    }

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
                        painter = painterResource(id = R.drawable.ic_leafpic), //take picture wala pic heal your crop ke niche
                        contentDescription = "Take a picture",
                        modifier = Modifier.size(48.dp)
                    )
                    Text(text = "Take a picture", fontSize = 12.sp, modifier = Modifier.padding(end = 16.dp))

                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_light_mobile), // mobile ka icon
                        contentDescription = "See diagnosis",
                        modifier = Modifier.size(48.dp)
                    )
                    Text(text = "See diagnosis", fontSize = 12.sp, modifier = Modifier.padding(end = 16.dp))
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_spray_bottle_thin), // bottle ka icon
                        contentDescription = "Get medicine",
                        modifier = Modifier.size(48.dp)
                    )
                    Text(text = "Get medicine", fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            Button(onClick = {
                val permissionCheckResult =
                    ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)

                if (permissionCheckResult == PackageManager.PERMISSION_GRANTED)
                {
                    cameraLauncher.launch(uri)
                }
                else
                {
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }) {
                Text(text = "Take a picture")
            }
        }
    }
}

fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyy_MM_dd_HH:mm:ss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )

    return image
}


@Composable
fun OptionCard(
    iconRes: Int,
    title: String,
    onClick: () -> Unit // This lambda function will be called when the card is clicked
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .size(160.dp)
            .padding(8.dp)
            .clickable(onClick = onClick), // Adds clickable functionality with the given lambda
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), // Add padding inside the card for better spacing
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

@Composable
fun OptionsGridSection(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OptionCard(

                iconRes = R.drawable.ic_calculator_thin,



                title = "Fertilizer calculator",
                onClick = { navController.navigate("fertilizerCalculator") }
            )
            OptionCard(

                iconRes = R.drawable.ic_bug_light,



                title = "Pests & diseases",
                onClick = { navController.navigate("pestsDiseases") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OptionCard(

                iconRes = R.drawable.ic_light_bulb_tips,

                title = "Cultivation Tips",
                onClick = { navController.navigate("cultivationTips") }
            )
            OptionCard(

                iconRes = R.drawable.ic_light_warning,

                title = "Pests and Disease Alerts",
                onClick = { navController.navigate("diseaseAlerts") }
            )
        }



        // New row for YouTube videos
    }

}
@Composable
fun YouTubeSection(navController: NavController) {
    val viewModel: YouTubeViewModel = viewModel()
    val videoList by viewModel.videos.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)

    ) {
        Text(
            text = "Watch YouTube Videos",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)        )


        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(videoList) { video ->
                YouTubeVideoItem(videoId = "lDOkIrrTEhs")
                YouTubeVideoItem(videoId = "8QO7YIvSQPc")
                YouTubeVideoItem(videoId = "ilpcW_dn4lI")
                YouTubeVideoItem(videoId = "L69l69ApLrw")
                YouTubeVideoItem(videoId = "1P8xirYNBbE")
            }
        }
    }
}


@Composable
fun YouTubeVideoItem(videoId: String) {
        val videoUrl = "https://www.youtube.com/embed/$videoId"
        val lifecycleOwner = LocalLifecycleOwner.current
        val context = LocalContext.current

            // YouTubePlayerView to display YouTube video
    AndroidView(
        modifier = Modifier
            .size(200.dp)
            .padding(horizontal = 4.dp),
        factory = { context ->
            YouTubePlayerView(context).apply {
                lifecycleOwner.lifecycle.addObserver(this)
                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.cueVideo(videoId, 0f)
                    }
                })
            }
        }
    )
}




//@Composable
//fun OptionCard(iconRes: Int, title: String) {
//    Card(
//        shape = RoundedCornerShape(16.dp),
//        modifier = Modifier
//            .size(160.dp)
//            .padding(8.dp),
//        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9))
//    ) {
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Icon(
//                painter = painterResource(id = iconRes),
//                contentDescription = title,
//                modifier = Modifier.size(48.dp)
//            )
//            Text(text = title, fontSize = 14.sp)
//        }
//    }
//}
//@Composable
//fun BottomNavigationBar(navController: NavController) {
//    val items = listOf(
//        BottomNavItem.YourCrops,
//        BottomNavItem.Community,
//        BottomNavItem.Dukaan,
//        BottomNavItem.You
//    )
//
//    NavigationBar(
//        containerColor = Color(0xFFE0F2F1) // Background color similar to the image
//    ) {
//        val navBackStackEntry by navController.currentBackStackEntryAsState()
//        val currentDestination = navBackStackEntry?.destination
//
//        items.forEach { item ->
//            NavigationBarItem(
//                icon = {
//                    Icon(
//                        painter = painterResource(id = item.icon),
//                        contentDescription = item.title
//                    )
//                },
//                label = { Text(text = item.title) },
//                selected = currentDestination?.route == item.route,
//                onClick = {
//                    navController.navigate(item.route) {
//                        // Handle navigation stack and ensure a single instance of the screen
//                        popUpTo(navController.graph.startDestinationId) { saveState = true }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
//                }
//            )
//        }
//    }
//}
//
//sealed class BottomNavItem(val route: String, val icon: Int, val title: String) {
//    object YourCrops : BottomNavItem("your_crops", R.drawable.handwash, "Your Crops")
//    object Community : BottomNavItem("community", R.drawable.handwash, "Community")
//    object Dukaan : BottomNavItem("dukaan", R.drawable.handwash, "Dukaan")
//    object You : BottomNavItem("you", R.drawable.handwash, "You")
//}
