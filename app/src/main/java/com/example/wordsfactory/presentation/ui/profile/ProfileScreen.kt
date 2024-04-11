package com.example.wordsfactory.presentation.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wordsfactory.R
import com.example.wordsfactory.presentation.ui.utils.AccentButton
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = koinViewModel(), onLogout: () -> Unit) {
    // todo logout button
    // todo statistics
    // todo user info
    val state by viewModel.profileState.collectAsStateWithLifecycle()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().padding(26.dp)
    ) {
        Text(state.user?.name ?: "User", style = MaterialTheme.typography.displaySmall)
        Spacer(modifier = Modifier.padding(32.dp))
        AccentButton(
            onClick = {
                viewModel.logout()
                onLogout()
            },
            isEnabled = true,
            text = stringResource(R.string.logout)
        )
    }
}