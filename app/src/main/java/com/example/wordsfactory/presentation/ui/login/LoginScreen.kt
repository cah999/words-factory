package com.example.wordsfactory.presentation.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wordsfactory.R
import com.example.wordsfactory.di.appModule
import com.example.wordsfactory.presentation.ui.utils.AccentButton
import com.example.wordsfactory.presentation.ui.utils.InputField
import com.example.wordsfactory.presentation.ui.utils.UiState
import com.example.wordsfactory.ui.theme.Dark
import com.example.wordsfactory.ui.theme.DarkGrey
import com.example.wordsfactory.ui.theme.Error
import com.example.wordsfactory.ui.theme.LightGrey
import com.example.wordsfactory.ui.theme.Primary
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication

@Composable
fun LoginScreen(
    onRegistrationNavigation: () -> Unit,
    onSuccessfulLogin: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    val loginUiState by viewModel.loginUiState.collectAsStateWithLifecycle()
    val loginState by viewModel.loginState.collectAsStateWithLifecycle()

    if (loginUiState is UiState.Success) {
        LaunchedEffect(Unit) {
            onSuccessfulLogin()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = rememberScrollState())
            .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = null,
            modifier = Modifier.height(212.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.log_in),
            style = MaterialTheme.typography.headlineLarge,
            color = Dark
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.sign_in_to_your_account),
            style = MaterialTheme.typography.bodyMedium,
            color = DarkGrey
        )
        Spacer(modifier = Modifier.height(16.dp))
        InputField(
            value = loginState.emailText,
            onValueChange = { viewModel.onEmailTextChanged(it) },
            label = stringResource(R.string.email)
        )
        Spacer(modifier = Modifier.height(16.dp))
        InputField(value = loginState.passwordText,
            onValueChange = { viewModel.onPasswordTextChanged(it) },
            label = stringResource(R.string.password),
            isPassword = true,
            isPasswordVisible = loginState.passwordVisible,
            onButtonToggle = { viewModel.onPasswordVisibilityChanged() })

        if (loginUiState is UiState.Error) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = (loginUiState as UiState.Error).message,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start),
                color = Error
            )
        }
        if (loginUiState is UiState.Loading) {
            Spacer(modifier = Modifier.height(16.dp))
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(), color = Primary, trackColor = LightGrey
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        AccentButton(
            modifier = Modifier.padding(top = 32.dp),
            onClick = {
                viewModel.login()
            }, isEnabled = loginState.isButtonEnabled, text = stringResource(R.string.log_in)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(R.string.create_acconut),
            style = MaterialTheme.typography.bodyMedium,
            color = DarkGrey,
            modifier = Modifier.clickable {
                onRegistrationNavigation()
            })
    }
}


@Composable
@Preview
fun PreviewLoginScreen() {
    KoinApplication(application = {
        modules(appModule)
    }) {
        LoginScreen({}, {})
    }
}

