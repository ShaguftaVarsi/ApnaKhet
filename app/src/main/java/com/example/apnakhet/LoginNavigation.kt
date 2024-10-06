package com.example.apnakhet.ui.theme.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apnakhet.AuthViewModel
import com.example.apnakhet.pages.FertilizerCalculator

import com.example.apnakhet.pages.cultivationTips

import com.example.apnakhet.login.HomePage
import com.example.apnakhet.login.LoginPage
import com.example.apnakhet.login.SignupPage
import com.example.apnakhet.pages.CommunityScreen
import com.example.apnakhet.pages.MyAccount
import com.example.apnakhet.pages.ShopScreen

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

        composable("fertilizerCalculator") {
            FertilizerCalculator(navController)
        }
        composable("cultivationTips") {
            cultivationTips(navController)
        }
        composable("diseaseAlerts") {
            cultivationTips(navController)
        }


        composable("community") { CommunityScreen() }
        composable("shop") { ShopScreen() }
        composable("you") { MyAccount() }
    })
}

