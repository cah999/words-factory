package com.example.wordsfactory.presentation.ui.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wordsfactory.R
import com.example.wordsfactory.common.Constants
import com.example.wordsfactory.presentation.navigation.Screen
import com.example.wordsfactory.ui.theme.Dark
import com.example.wordsfactory.ui.theme.DarkGrey
import com.example.wordsfactory.ui.theme.Grey
import com.example.wordsfactory.ui.theme.PrimaryColor
import com.example.wordsfactory.ui.theme.White
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = koinViewModel()
) {
    val nameText = remember { mutableStateOf(Constants.EMPTY) }
    val emailText = remember { mutableStateOf(Constants.EMPTY) }
    val passwordText = remember { mutableStateOf(Constants.EMPTY) }
    val passwordVisible = remember { mutableStateOf(false) }
    val isButtonEnabled =
        remember { mutableStateOf(nameText.value.isNotEmpty() && emailText.value.isNotEmpty()) }


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
            value = nameText.value,
            onValueChange = { nameText.value = it },
            label = stringResource(R.string.name)
        )
        Spacer(modifier = Modifier.height(16.dp))
        InputField(
            value = emailText.value,
            onValueChange = { emailText.value = it },
            label = stringResource(R.string.email)
        )
        Spacer(modifier = Modifier.height(16.dp))
        InputField(
            value = passwordText.value,
            onValueChange = { passwordText.value = it },
            label = stringResource(R.string.password),
            isPassword = true,
            isPasswordVisible = passwordVisible
        )
        Spacer(modifier = Modifier.weight(1f))
        AccentButton(
            onClick = { navController.navigate(Screen.Dictionary.route) },
            isEnabled = isButtonEnabled,
            text = stringResource(R.string.sign_up)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(R.string.log_in),
            style = MaterialTheme.typography.bodyMedium,
            color = DarkGrey,
            modifier = Modifier.clickable { navController.navigate(Screen.Login.route) })
    }
}


@Composable
@Preview
fun PreviewSignUpScreen() {
    SignUpScreen(rememberNavController())
}

@Composable
fun AccentButton(onClick: () -> Unit, isEnabled: MutableState<Boolean>, text: String) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .alpha(if (isEnabled.value) 1f else 0.45f),
        shape = RoundedCornerShape(size = 10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryColor,
            disabledContainerColor = PrimaryColor,
        ),
        enabled = isEnabled.value
    ) {
        Text(
            text = text, style = MaterialTheme.typography.labelMedium, color = White
        )
    }
}

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false,
    isPasswordVisible: MutableState<Boolean>? = null
) {
    OutlinedTextField(value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label, style = MaterialTheme.typography.bodyMedium, color = DarkGrey
            )
        },
        textStyle = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Grey,
            unfocusedBorderColor = DarkGrey,
            focusedTextColor = Dark,
            unfocusedTextColor = Dark,
        ),
        visualTransformation = if (isPassword) {
            if (isPasswordVisible?.value == true) VisualTransformation.None else PasswordVisualTransformation()
        } else VisualTransformation.None,
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = {
                    isPasswordVisible?.value = !(isPasswordVisible?.value ?: true)
                }) {
                    Icon(
                        painter = painterResource(id = if (isPasswordVisible?.value == true) R.drawable.eye_opened else R.drawable.eye_closed),
                        contentDescription = null
                    )
                }
            }
        })
}