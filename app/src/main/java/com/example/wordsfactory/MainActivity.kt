package com.example.wordsfactory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.wordsfactory.presentation.navigation.AppNavigation
import com.example.wordsfactory.presentation.navigation.BottomBar
import com.example.wordsfactory.presentation.navigation.Screen
import com.example.wordsfactory.ui.theme.WordsFactoryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Splash.route

            WordsFactoryTheme {
                Scaffold(bottomBar = {
                    when (currentRoute) {
                        Screen.Dictionary.route, Screen.Training.route, Screen.Video.route -> {
                            BottomBar(navController = navController)
                        }

                        else -> {}
                    }
                }) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        AppNavigation(navController = navController)
                    }

                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WordsFactoryTheme {
        Greeting("Android")
    }
}