package com.example.wordsfactory.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BottomBar(navController: NavController) {
    Column {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Color(0xA6545458)
        )
        NavigationBar(
            containerColor = Color(0xFF161616),
            modifier = Modifier
                .width(360.dp)
                .height(67.dp)
        ) {
            val screensToDisplay = listOf(Screen.Dictionary, Screen.Training, Screen.Video)
            val currentScreen = navController.currentBackStackEntry?.destination?.route

            screensToDisplay.forEach { screen ->
                NavigationBarItem(
                    icon = {
                        screen.imageResource?.let { resource ->
                            Icon(
                                painter = painterResource(resource),
                                contentDescription = null
                            )
                        }
                    },
                    label = {
                        Text(
                            text = screen.title,
                        )
                    },
                    selected = currentScreen == screen.route,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                )
            }
        }
    }
}