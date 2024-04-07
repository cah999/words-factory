package com.example.wordsfactory.presentation.ui.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.wordsfactory.R
import com.example.wordsfactory.ui.theme.Dark
import com.example.wordsfactory.ui.theme.DarkGrey
import com.example.wordsfactory.ui.theme.Grey

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false,
    isPasswordVisible: Boolean? = null,
    onButtonToggle: () -> Unit = {}
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
            focusedBorderColor = DarkGrey,
            unfocusedBorderColor = Grey,
            focusedTextColor = Dark,
            unfocusedTextColor = Dark,
        ),
        visualTransformation = if (isPassword) {
            if (isPasswordVisible == true) VisualTransformation.None else PasswordVisualTransformation()
        } else VisualTransformation.None,
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = {
                    onButtonToggle()
                }) {
                    Icon(
                        painter = painterResource(id = if (isPasswordVisible == true) R.drawable.eye_opened else R.drawable.eye_closed),
                        contentDescription = null
                    )
                }
            }
        })
}