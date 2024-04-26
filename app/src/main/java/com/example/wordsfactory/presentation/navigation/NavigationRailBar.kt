package com.example.wordsfactory.presentation.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wordsfactory.ui.theme.Grey
import com.example.wordsfactory.ui.theme.Primary

@Composable
fun NavigationRailBar(navController: NavController) {
    androidx.compose.material3.NavigationRail {
        val screensToDisplay =
            listOf(Screen.Dictionary, Screen.Training, Screen.Video, Screen.Profile)
        val currentScreen = navController.currentBackStackEntry?.destination?.route

        screensToDisplay.forEach { screen ->
            NavigationRailItem(
                icon = {
                    screen.imageResource?.let { resource ->
                        Icon(
                            painter = painterResource(resource),
                            contentDescription = null,
                            tint = if (currentScreen == screen.route) Primary else Grey,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                label = {
                    Text(
                        text = screen.title,
                        color = if (currentScreen == screen.route) Primary else Grey,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                selected = currentScreen == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                colors = NavigationRailItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}