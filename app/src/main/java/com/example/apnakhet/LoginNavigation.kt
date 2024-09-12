package com.example.apnakhet.ui.theme.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apnakhet.AuthViewModel
import com.example.apnakhet.FertilizerCalculator
import com.example.apnakhet.imageCaptureFromCamera
import com.example.apnakhet.login.HomePage
import com.example.apnakhet.login.LoginPage
import com.example.apnakhet.login.SignupPage

@Composable
fun LoginNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login", builder = {
        composable("login") {
            LoginPage(modifier, navController, authViewModel)
        }
        composable("signup") {
            SignupPage(modifier, navController, authViewModel)
        }
        composable("home") {
            HomePage(modifier, navController, authViewModel)
        }

        composable("camera") {
            imageCaptureFromCamera()
        }

        composable("fertilizerCalculator") {
            FertilizerCalculator(navController)
        }
    })
}