package com.example.wordsfactory.presentation.ui.training

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wordsfactory.R
import com.example.wordsfactory.presentation.ui.utils.AccentButton
import com.example.wordsfactory.ui.theme.Dark
import com.example.wordsfactory.ui.theme.Primary

@Composable
fun TrainingScreen(onStartNavigate: () -> Unit) {
    val count = 25
    val text = stringResource(R.string.training_screen).format(count)
    // do count with primary color
    val annotatedString = buildAnnotatedString {
        withStyle(SpanStyle(color = Dark)) {
            append(text.substring(0, text.indexOf(count.toString())))
        }
        withStyle(SpanStyle(color = Primary)) {
            append(count.toString())
        }
        withStyle(SpanStyle(color = Dark)) {
            append(text.substring(text.indexOf(count.toString()) + count.toString().length))
        }
    }
    Box(
        modifier = Modifier
            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = annotatedString,
                style = MaterialTheme.typography.headlineLarge,
                color = Dark,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.start_training),
                style = MaterialTheme.typography.headlineLarge,
                color = Dark
            )
        }
        AccentButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            onClick = { /*TODO*/ },
            isEnabled = true,
            text = stringResource(R.string.start)
        )
    }

}

@Composable
@Preview
fun TrainingScreenPreview() {
    TrainingScreen({})
}