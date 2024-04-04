package com.example.wordsfactory.presentation.ui.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wordsfactory.R
import com.example.wordsfactory.di.appModule
import com.example.wordsfactory.di.dataModule
import com.example.wordsfactory.di.domainModule
import com.example.wordsfactory.presentation.navigation.Screen
import com.example.wordsfactory.presentation.ui.utils.AccentButton
import com.example.wordsfactory.presentation.ui.utils.InputField
import com.example.wordsfactory.presentation.ui.utils.UiState
import com.example.wordsfactory.ui.theme.Dark
import com.example.wordsfactory.ui.theme.DarkGrey
import com.example.wordsfactory.ui.theme.Error
import com.example.wordsfactory.ui.theme.LightGrey
import com.example.wordsfactory.ui.theme.PrimaryColor
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication

@Composable
fun SignUpScreen(
    navController: NavController, viewModel: SignUpViewModel = koinViewModel()
) {
    val signUpUiState by viewModel.signUpUiState.collectAsState()
    val nameText by viewModel.nameText.collectAsState()
    val emailText by viewModel.emailText.collectAsState()
    val passwordText by viewModel.passwordText.collectAsState()
    val passwordVisible by viewModel.passwordVisible.collectAsState()
    val isButtonEnabled by viewModel.isButtonEnabled.collectAsState(false)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.signup),
            contentDescription = null,
            modifier = Modifier.height(212.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.sign_up),
            style = MaterialTheme.typography.headlineLarge,
            color = Dark
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.create_your_account),
            style = MaterialTheme.typography.bodyMedium,
            color = DarkGrey
        )
        Spacer(modifier = Modifier.height(16.dp))
        InputField(
            value = nameText,
            onValueChange = { viewModel.nameText.value = it },
            label = stringResource(R.string.name)
        )
        Spacer(modifier = Modifier.height(16.dp))
        InputField(
            value = emailText,
            onValueChange = { viewModel.emailText.value = it },
            label = stringResource(R.string.email)
        )
        Spacer(modifier = Modifier.height(16.dp))
        InputField(
            value = passwordText,
            onValueChange = { viewModel.passwordText.value = it },
            label = stringResource(R.string.password),
            isPassword = true,
            isPasswordVisible = passwordVisible,
            onButtonToggle = { viewModel.passwordVisible.value = !passwordVisible }
        )
        if (signUpUiState is UiState.Error) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = (signUpUiState as UiState.Error).message,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start),
                color = Error
            )
        }
        if (signUpUiState is UiState.Loading) {
            Spacer(modifier = Modifier.height(16.dp))
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                color = PrimaryColor,
                trackColor = LightGrey
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        AccentButton(
            onClick = {
                viewModel.signUp()
//                navController.navigate(Screen.Dictionary.route)
            },
            isEnabled = isButtonEnabled,
            text = stringResource(R.string.sign_up)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(R.string.log_in),
            style = MaterialTheme.typography.bodyMedium,
            color = DarkGrey,
            modifier = Modifier.clickable {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Registration.route) { inclusive = true }
                }
            })
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewSignUpScreen() {
    KoinApplication(application = {
        modules(appModule, dataModule, domainModule)
    }) {
        SignUpScreen(rememberNavController())
    }

}
