package com.example.wordsfactory.presentation.ui.dictionary

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wordsfactory.R
import com.example.wordsfactory.presentation.ui.dictionary.elements.DictionaryScreenLoading
import com.example.wordsfactory.presentation.ui.dictionary.elements.DictionaryScreenPlaceholder
import com.example.wordsfactory.presentation.ui.dictionary.elements.ErrorAlertDialog
import com.example.wordsfactory.presentation.ui.dictionary.elements.SearchRow
import com.example.wordsfactory.presentation.ui.utils.UiState.Default
import com.example.wordsfactory.presentation.ui.utils.UiState.Error
import com.example.wordsfactory.presentation.ui.utils.UiState.Loading
import com.example.wordsfactory.presentation.ui.utils.UiState.Success
import com.example.wordsfactory.ui.theme.Primary
import com.example.wordsfactory.ui.theme.White
import org.koin.androidx.compose.koinViewModel

@Composable
fun DictionaryScreen(viewModel: DictionaryViewModel = koinViewModel()) {
    val dictionaryUiState by viewModel.dictionaryUiState.collectAsStateWithLifecycle()
    val dictionaryState by viewModel.dictionaryState.collectAsStateWithLifecycle()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            SearchRow(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 16.dp, end = 16.dp),
                search = dictionaryState.searchText,
                currentPage = dictionaryState.currentPage,
                totalPages = dictionaryState.wordContent?.size ?: 0,
                onSearchValueChange = {
                    viewModel.onSearchTextChanged(it)
                },
                onSearch = { viewModel.onSearchTextChanged(dictionaryState.searchText) })
            when (dictionaryUiState) {
                is Loading -> DictionaryScreenLoading()
                is Default -> DictionaryScreenPlaceholder(modifier = Modifier.padding(top = 52.dp))
                is Error -> ErrorAlertDialog(
                    onDismiss = { viewModel.onDismissError() },
                    errorText = (dictionaryUiState as Error).message
                )

                is Success -> if (dictionaryState.wordContent != null) {
                    DictionaryScreenContent(
                        modifier = Modifier.padding(top = 16.dp),
                        wordContents = dictionaryState.wordContent!!,
                        viewModel = viewModel
                    )
                    Spacer(modifier = Modifier.height(60.dp))
                } else DictionaryScreenPlaceholder(
                    modifier = Modifier.padding(top = 52.dp)
                )
            }
        }
        if (dictionaryUiState is Success) {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd)
                    .padding(start = 26.dp, end = 26.dp, bottom = 8.dp, top = 13.dp)
                    .height(56.dp),
                onClick = {
                    if (!dictionaryState.isFavorite) viewModel.addToDictionary() else viewModel.removeFromDictionary()
                },
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 0.dp
                ),
                containerColor = Primary,
                contentColor = White,
                content = {
                    Text(
                        text = if (dictionaryState.isFavorite) stringResource(R.string.remove_from_dictionary) else stringResource(
                            R.string.add_to_dictionary
                        ), style = MaterialTheme.typography.labelMedium, color = White
                    )
                },
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun DictPlaceholderPrev() {
    DictionaryScreen()
}