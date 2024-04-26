package com.example.wordsfactory.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.wordsfactory.presentation.ui.dictionary.DictionaryScreen
import com.example.wordsfactory.presentation.ui.finish.FinishScreen
import com.example.wordsfactory.presentation.ui.intro.IntroScreen
import com.example.wordsfactory.presentation.ui.login.LoginScreen
import com.example.wordsfactory.presentation.ui.profile.ProfileScreen
import com.example.wordsfactory.presentation.ui.question.QuestionScreen
import com.example.wordsfactory.presentation.ui.signup.SignUpScreen
import com.example.wordsfactory.presentation.ui.splash.SplashScreen
import com.example.wordsfactory.presentation.ui.training.TrainingScreen
import com.example.wordsfactory.presentation.video.VideoScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
) {
    NavHost(
        navController, startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onSuccessNavigate = {
                navController.navigate(Screen.Dictionary.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            }, onFailedNavigate = {
                navController.navigate(Screen.Intro.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Registration.route) {
            SignUpScreen(onSuccessfulRegistration = {
                navController.navigate(Screen.Dictionary.route) {
                    popUpTo(Screen.Registration.route) { inclusive = true }
                }
            }, onLoginNavigation = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Registration.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Login.route) {
            LoginScreen(onRegistrationNavigation = {
                navController.navigate(Screen.Registration.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            }, onSuccessfulLogin = {
                navController.navigate(Screen.Dictionary.route) {
                    popUpTo(Screen.Intro.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Intro.route) {
            IntroScreen(onNavigation = { navController.navigate(Screen.Registration.route) })
        }
        composable(Screen.Dictionary.route) {
            DictionaryScreen()
        }
        composable(Screen.Training.route) {
            TrainingScreen(onStartNavigate = {
                navController.navigate(Screen.Question.route)
            })
        }
        composable(Screen.Question.route) {
            QuestionScreen(onNavigate = { correctCount, totalCount ->
                navController.navigate("${Screen.Finish.route}/$correctCount/$totalCount") {
                    popUpTo(Screen.Question.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Video.route) {
            VideoScreen()
        }
        composable(Screen.Profile.route) {
            ProfileScreen(onLogout = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Profile.route) { inclusive = true }
                }
            })
        }
        composable(
            "${Screen.Finish.route}/{correctCount}/{totalCount}",
            arguments = listOf(navArgument("correctCount") { type = NavType.IntType },
                navArgument("totalCount") { type = NavType.IntType })
        ) {
            val correctCount = it.arguments?.getInt("correctCount") ?: 0
            val totalCount = it.arguments?.getInt("totalCount") ?: 0
            FinishScreen(correctCount = correctCount, totalCount = totalCount, onNavigateBack = {
                navController.navigate(Screen.Training.route) {
                    popUpTo(Screen.Finish.route) { inclusive = true }
                }
            }, onNavigateAgain = { navController.navigate(Screen.Question.route) })
        }
    }
}


