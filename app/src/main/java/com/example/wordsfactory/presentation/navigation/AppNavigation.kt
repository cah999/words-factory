package com.example.wordsfactory.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.wordsfactory.presentation.ui.intro.IntroScreen
import com.example.wordsfactory.presentation.ui.login.LoginScreen
import com.example.wordsfactory.presentation.ui.signup.SignUpScreen
import com.example.wordsfactory.presentation.ui.splash.SplashScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
) {
    NavHost(
        navController, startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.Registration.route) {
            SignUpScreen(navController = navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.Intro.route) {
            IntroScreen(navController = navController)
        }

    }
}


