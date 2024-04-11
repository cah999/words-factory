package com.example.wordsfactory.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.wordsfactory.presentation.ui.dictionary.DictionaryScreen
import com.example.wordsfactory.presentation.ui.finish.FinishScreen
import com.example.wordsfactory.presentation.ui.intro.IntroScreen
import com.example.wordsfactory.presentation.ui.login.LoginScreen
import com.example.wordsfactory.presentation.ui.question.QuestionScreen
import com.example.wordsfactory.presentation.ui.signup.SignUpScreen
import com.example.wordsfactory.presentation.ui.splash.SplashScreen
import com.example.wordsfactory.presentation.ui.training.TrainingScreen
import com.example.wordsfactory.presentation.video.VideoScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
) {
    // todo что делать с загрузкой страницы каждый раз при нажатии на нижнюю панель
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
            TrainingScreen(onStartNavigate = { navController.navigate(Screen.Question.route) })
        }
        composable(Screen.Question.route) {
            QuestionScreen(onNavigate = { navController.navigate(Screen.Video.route) })
        }
        composable(Screen.Video.route) {
            VideoScreen()
        }
        composable(Screen.Profile.route) {
            // todo экран профиля
        }
        composable(Screen.Finish.route) {
            FinishScreen(onNavigateBack = {
                navController.navigate(Screen.Training.route) {
                    popUpTo(Screen.Finish.route) { inclusive = true }
                }
            }, onNavigateAgain = { navController.navigate(Screen.Question.route) })
        }
    }
}


