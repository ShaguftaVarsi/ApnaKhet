package com.example.apnakhet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.apnakhet.Items.BottomNavigationBar
import com.example.apnakhet.Items.TopBar
import com.example.apnakhet.ui.theme.ApnaKhetTheme
import com.example.apnakhet.ui.theme.login.LoginNavigation

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authViewModel: AuthViewModel by viewModels()
        setContent {
            val navController = rememberNavController()  // Navigation controller
            ApnaKhetTheme {
                Scaffold(
                    topBar = {
                        TopBar(navController)  // Pass the navController
                    },
                    bottomBar = {
                        BottomNavigationBar(navController,authViewModel)
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    LoginNavigation(
                        modifier = Modifier.padding(innerPadding),
                        authViewModel = authViewModel,

                    )

                }

            }
        }
    }
}

