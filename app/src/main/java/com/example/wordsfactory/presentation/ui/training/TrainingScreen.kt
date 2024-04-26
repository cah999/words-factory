package com.example.wordsfactory.presentation.ui.training

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wordsfactory.R
import com.example.wordsfactory.common.Constants
import com.example.wordsfactory.di.appModule
import com.example.wordsfactory.di.dataModule
import com.example.wordsfactory.di.domainModule
import com.example.wordsfactory.presentation.ui.training.elements.InfiniteProgressBar
import com.example.wordsfactory.presentation.ui.training.elements.NotificationPermissionDialog
import com.example.wordsfactory.presentation.ui.utils.AccentButton
import com.example.wordsfactory.ui.theme.Dark
import com.example.wordsfactory.ui.theme.Primary
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TrainingScreen(onStartNavigate: () -> Unit, viewModel: TrainingViewModel = koinViewModel()) {
    val trainingState by viewModel.trainingState.collectAsStateWithLifecycle()
    val text = stringResource(R.string.training_screen).format(trainingState.count)
    val annotatedString = buildAnnotatedString {
        withStyle(SpanStyle(color = Dark)) {
            append(text.substring(0, text.indexOf(trainingState.count.toString())))
        }
        withStyle(SpanStyle(color = Primary)) {
            append(trainingState.count.toString())
        }
        withStyle(SpanStyle(color = Dark)) {
            append(text.substring(text.indexOf(trainingState.count.toString()) + trainingState.count.toString().length))
        }
    }

    val showNotificationDialog = remember { mutableStateOf(false) }
    val notificationPermissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(
            permission = Manifest.permission.POST_NOTIFICATIONS
        )
    } else {
        rememberPermissionState(
            permission = Manifest.permission.ACCESS_NOTIFICATION_POLICY
        )
    }
    Column(
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(bottom = 16.dp, start = 26.dp, end = 26.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.weight(1f))
        if (trainingState.count == 0) {
            Text(
                text = stringResource(R.string.no_words),
                style = MaterialTheme.typography.headlineLarge,
                color = Dark,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.weight(1f))
        } else {
            Text(
                text = annotatedString,
                style = MaterialTheme.typography.headlineLarge,
                color = Dark,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.start_training),
                style = MaterialTheme.typography.headlineLarge,
                color = Dark,
            )
            Spacer(modifier = Modifier.weight(1f))
            if (!trainingState.timerStarted) {
                AccentButton(
                    modifier = Modifier, onClick = {
                        if (!(notificationPermissionState.status.isGranted || Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU)) {
                            showNotificationDialog.value = true
                        } else {
                            viewModel.onTimerStartedChanged(true)
                        }
                    }, isEnabled = true, text = stringResource(R.string.start)
                )
            } else {
                InfiniteProgressBar(
                    modifier = Modifier.padding(bottom = 64.dp),
                    onFinishedNavigate = onStartNavigate,
                    time = Constants.TIMER_TIME
                )
            }
        }
    }

    if (showNotificationDialog.value) NotificationPermissionDialog(showNotificationDialog = showNotificationDialog,
        notificationPermissionState = notificationPermissionState,
        onResult = {
            viewModel.onTimerStartedChanged(true)
        })
}


@Composable
@Preview
fun TrainingScreenPreview() {
    KoinApplication(application = {
        modules(appModule, dataModule, domainModule)
    }) {
        TrainingScreen({})
    }
}