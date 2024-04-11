package com.example.wordsfactory.presentation.navigation

import com.example.wordsfactory.R

sealed class Screen(val route: String, val title: String, val imageResource: Int? = null) {
    data object Splash : Screen("splash", "Splash", null)
    data object Intro : Screen("intro", "Intro", null)
    data object Login : Screen("login", "Login", null)
    data object Registration : Screen("registration", "Registration", null)
    data object Question : Screen("question", "Question", null)
    data object Finish : Screen("finish", "Finish", null)
    data object Dictionary : Screen(
        "dictionary", "Dictionary",
        R.drawable.courses
    )

    data object Training : Screen("training", "Training", R.drawable.training)

    data object Video : Screen("video", "Video", R.drawable.video)
    data object Profile : Screen("profile", "Profile", R.drawable.user)
}