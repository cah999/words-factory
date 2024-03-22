package com.example.wordsfactory.presentation.navigation

import com.example.wordsfactory.R

sealed class Screen(val route: String, val title: String, val imageResource: Int? = null) {
    data object Splash : Screen("splash", "Splash", null)
    data object Intro : Screen("intro", "Intro", null)
    data object Login : Screen("login", "Login", null)
    data object Registration : Screen("registration", "Registration", null)
    data object Dictionary : Screen(
        "dictionary", "Dictionary",
        android.R.drawable.alert_light_frame
    )

    data object Training : Screen("training", "Training", android.R.drawable.alert_light_frame)

    data object Video : Screen("video", "Video", android.R.drawable.alert_light_frame)
}