package com.example.wordsfactory.presentation.navigation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
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
import com.example.wordsfactory.ui.theme.White

@Composable
fun BottomBar(navController: NavController) {
    NavigationBar(
        containerColor = White, contentColor = White,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .border(1.dp, Grey, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))

    ) {
        val screensToDisplay = listOf(Screen.Dictionary, Screen.Training, Screen.Video)
        val currentScreen = navController.currentBackStackEntry?.destination?.route

        screensToDisplay.forEach { screen ->
            NavigationBarItem(
                icon = {
                    screen.imageResource?.let { resource ->
                        Icon(
                            painter = painterResource(resource),
                            contentDescription = null,
                            tint = if (currentScreen == screen.route) Primary else Grey
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
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}


@Composable
fun NavigationRailBar(navController: NavController) {
    androidx.compose.material3.NavigationRail {
        val screensToDisplay = listOf(Screen.Dictionary, Screen.Training, Screen.Video)
        val currentScreen = navController.currentBackStackEntry?.destination?.route

        screensToDisplay.forEach { screen ->
            NavigationRailItem(
                icon = {
                    screen.imageResource?.let { resource ->
                        Icon(
                            painter = painterResource(resource),
                            contentDescription = null,
                            tint = if (currentScreen == screen.route) Primary else Grey
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