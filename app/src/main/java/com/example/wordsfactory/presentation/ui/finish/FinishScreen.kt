package com.example.wordsfactory.presentation.ui.finish

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wordsfactory.R
import com.example.wordsfactory.presentation.ui.utils.AccentButton

@Composable
fun FinishScreen(onNavigateBack: () -> Unit, onNavigateAgain: () -> Unit) {
    val correct by remember { mutableIntStateOf(0) }
    val incorrect by remember { mutableIntStateOf(10) }
    Box {
        IconButton(onClick = { onNavigateBack() }) {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "",
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id = R.drawable.finish), contentDescription = "")
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "Training is finished", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Correct: $correct", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Incorrect: $incorrect", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(32.dp))
            AccentButton(onClick = { onNavigateAgain() }, isEnabled = true, text = "Again")
        }
    }
}

@Preview
@Composable
private fun FinishScreenPreview() {
    FinishScreen({}, {})
}