package com.example.wordsfactory.presentation.ui.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.example.wordsfactory.ui.theme.Primary
import com.example.wordsfactory.ui.theme.White

@Composable
fun AccentButton(
    modifier: Modifier = Modifier, onClick: () -> Unit, isEnabled: Boolean, text: String
) {
    Button(
        onClick = { onClick() },
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .alpha(if (isEnabled) 1f else 0.45f),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary,
            disabledContainerColor = Primary,
        ),
        enabled = isEnabled
    ) {
        Text(
            text = text, style = MaterialTheme.typography.labelMedium, color = White
        )
    }
}