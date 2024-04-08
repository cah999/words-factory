package com.example.wordsfactory

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.wordsfactory.presentation.navigation.AppNavigation
import com.example.wordsfactory.presentation.navigation.BottomBar
import com.example.wordsfactory.presentation.navigation.NavigationRailBar
import com.example.wordsfactory.presentation.navigation.Screen
import com.example.wordsfactory.ui.theme.Grey
import com.example.wordsfactory.ui.theme.WordsFactoryTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

// todo спросить оставить ли cutout??
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.attributes.layoutInDisplayCutoutMode =
            WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES

        setContent {
            val systemUiController = rememberSystemUiController()
            systemUiController.setStatusBarColor(
                color = Grey
            )
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Splash.route

            WordsFactoryTheme {
                val windowClass = calculateWindowSizeClass(this)
                val showNavigationRail =
                    windowClass.widthSizeClass != WindowWidthSizeClass.Compact
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.displayCutout),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
//                        contentWindowInsets = WindowInsets(0.dp),
                        bottomBar = {
                            when (currentRoute) {

                                Screen.Dictionary.route, Screen.Training.route, Screen.Video.route -> {
                                    if (!showNavigationRail) {
                                        BottomBar(navController = navController)
                                    }
                                }

                                else -> {}
                            }
                        }) {
                        Log.d("MainActivity", "padding: $it")
                        Box(
                            modifier = Modifier
                                .padding(it)
                                .fillMaxSize()
//                                .windowInsetsPadding(WindowInsets.safeContent)
                                .padding(
                                    // 80 if no cutout
                                    start = if (showNavigationRail) 45.dp else 0.dp
                                )
                        ) {
                            AppNavigation(navController = navController)
                        }
                    }
                }
                if (showNavigationRail) {
                    NavigationRailBar(navController = navController)
                }
            }
        }
    }
}