package com.example.wordsfactory.presentation.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wordsfactory.R
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


@Preview
@Composable
private fun SplashPreview() {
    SplashScreen(onSuccessNavigate = {}, onFailedNavigate = {})
}
