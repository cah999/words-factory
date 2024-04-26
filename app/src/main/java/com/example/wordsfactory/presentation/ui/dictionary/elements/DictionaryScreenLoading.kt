package com.example.wordsfactory.presentation.ui.dictionary.elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wordsfactory.ui.theme.Primary

@Composable
fun DictionaryScreenLoading() {
    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        color = Primary,
        trackColor = Primary.copy(alpha = 0.2f)
    )
}