package com.example.wordsfactory.presentation.ui.dictionary.elements

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.wordsfactory.ui.theme.White

@Composable
fun ErrorAlertDialog(onDismiss: () -> Unit, errorText: String) {
    AlertDialog(onDismissRequest = onDismiss,
        title = { Text(text = "Error", style = MaterialTheme.typography.headlineSmall) },
        text = { Text(text = errorText, style = MaterialTheme.typography.bodyMedium) },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text(text = "OK", style = MaterialTheme.typography.bodyMedium, color = White)
            }
        })
}