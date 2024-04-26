package com.example.wordsfactory.presentation.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wordsfactory.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import org.koin.androidx.compose.koinViewModel


@Composable
fun SplashScreen(
    onSuccessNavigate: () -> Unit,
    onFailedNavigate: () -> Unit,
    viewModel: SplashViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        if (viewModel.isUserLoggedIn()) {
            onSuccessNavigate()
        } else {
            onFailedNavigate()
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = null,
            modifier = Modifier
                .width(328.dp)
                .height(260.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.displayMedium,
        )

    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NotificationPermissionDialog(
    showNotificationDialog: MutableState<Boolean>,
    notificationPermissionState: PermissionState,
    onResult: () -> Unit
) {
    if (showNotificationDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showNotificationDialog.value = false
                notificationPermissionState.launchPermissionRequest()
            },
            title = {
                Text(
                    text = "Allow notification?",
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            text = {
                Text(
                    text = "We'll remind you of the practice if you haven't already had a practice today",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    showNotificationDialog.value = false
                    notificationPermissionState.launchPermissionRequest()
                    onResult()
                }) {
                    Text(text = "YES", style = MaterialTheme.typography.bodyMedium)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showNotificationDialog.value = false
                    onResult()
                }) {
                    Text(text = "NO", style = MaterialTheme.typography.bodyMedium)
                }
            }
        )
    }
}

@Preview
@Composable
private fun SplashPreview() {
    SplashScreen(onSuccessNavigate = {}, onFailedNavigate = {})
}

@OptIn(ExperimentalPermissionsApi::class)
@Preview
@Composable
private fun PreviewNotification() {
    val state = remember { mutableStateOf(true) }
    val permission =
        rememberPermissionState(permission = android.Manifest.permission.ACCESS_NOTIFICATION_POLICY)
    NotificationPermissionDialog(
        showNotificationDialog = state,
        notificationPermissionState =
        permission,
        onResult = {}
    )
}