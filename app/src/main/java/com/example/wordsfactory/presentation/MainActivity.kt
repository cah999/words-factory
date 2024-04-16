package com.example.wordsfactory.presentation

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
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
import com.example.wordsfactory.R
import com.example.wordsfactory.common.Constants
import com.example.wordsfactory.presentation.navigation.AppNavigation
import com.example.wordsfactory.presentation.navigation.BottomBar
import com.example.wordsfactory.presentation.navigation.NavigationRailBar
import com.example.wordsfactory.presentation.navigation.Screen
import com.example.wordsfactory.ui.theme.Grey
import com.example.wordsfactory.ui.theme.WordsFactoryTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

// todo floating button on dictionary screen
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences(getString(R.string.prefs), MODE_PRIVATE);
        val cutout = prefs.getInt(Constants.CUTOUT, 0)
        when (cutout) {
            0 -> window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT

            1 -> {
                enableEdgeToEdge()
                window.attributes.layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }
        }

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
                    windowClass.widthSizeClass != WindowWidthSizeClass.Compact && currentRoute in listOf(
                        Screen.Dictionary.route,
                        Screen.Training.route,
                        Screen.Video.route,
                        Screen.Profile.route
                    )

                val state = rememberTransformableState { zoomChange, _, _ ->
                    if (showNavigationRail) {
                        if (zoomChange < 1.0f) {
                            if (cutout == 0) return@rememberTransformableState
                            prefs.edit().putInt(
                                Constants.CUTOUT,
                                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT
                            ).apply()
                            recreate()
                        } else if (zoomChange > 1.0f) {
                            if (cutout == 1) return@rememberTransformableState
                            prefs.edit().putInt(
                                Constants.CUTOUT,
                                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
                            ).apply()
                            recreate()
                        }
                    }
                }
                val modifier =
                    if (showNavigationRail && currentRoute != Screen.Video.route) Modifier.transformable(
                        state
                    ) else Modifier
                // todo fix bottom bar height on pixel 3a
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.displayCutout),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(bottomBar = {
                        when (currentRoute) {
                            Screen.Dictionary.route, Screen.Training.route, Screen.Video.route, Screen.Profile.route -> {
                                if (!showNavigationRail) {
                                    BottomBar(navController = navController)
                                }
                            }

                            else -> {}
                        }
                    }) {
                        Box(
                            modifier = modifier
                                .padding(it)
                                .fillMaxSize()
                                .padding(
                                    start = if (showNavigationRail) (if (cutout == 1) 45.dp else 80.dp) else 0.dp
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