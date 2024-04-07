package com.example.wordsfactory.presentation.ui.dictionary

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wordsfactory.R
import com.example.wordsfactory.ui.theme.Dark
import com.example.wordsfactory.ui.theme.DarkGrey
import com.example.wordsfactory.ui.theme.Grey

@Composable
fun SearchRow(
    modifier: Modifier = Modifier,
    search: String,
    onSearchValueChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    OutlinedTextField(modifier = modifier,
        value = search,
        onValueChange = onSearchValueChange,
        textStyle = MaterialTheme.typography.bodyMedium,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = DarkGrey,
            unfocusedBorderColor = Grey,
            focusedTextColor = Dark,
            unfocusedTextColor = Dark,
        ),
        shape = RoundedCornerShape(12.dp),
        trailingIcon = {
            IconButton(onClick = { onSearch() }) {
                Icon(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = "",
                )
            }

        },
        placeholder = { Text(text = stringResource(R.string.search)) })

}