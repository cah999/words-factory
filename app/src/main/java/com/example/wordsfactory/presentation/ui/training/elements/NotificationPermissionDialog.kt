package com.example.wordsfactory.presentation.ui.training.elements

import android.Manifest
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState

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

@OptIn(ExperimentalPermissionsApi::class)
@Preview
@Composable
private fun PreviewNotification() {
    val state = remember { mutableStateOf(true) }
    val permission =
        rememberPermissionState(permission = Manifest.permission.ACCESS_NOTIFICATION_POLICY)
    NotificationPermissionDialog(
        showNotificationDialog = state,
        notificationPermissionState =
        permission,
        onResult = {}
    )
}