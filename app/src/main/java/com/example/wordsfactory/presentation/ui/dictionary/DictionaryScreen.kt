package com.example.wordsfactory.presentation.ui.dictionary

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wordsfactory.presentation.ui.utils.UiState.Default
import com.example.wordsfactory.presentation.ui.utils.UiState.Error
import com.example.wordsfactory.presentation.ui.utils.UiState.Loading
import com.example.wordsfactory.presentation.ui.utils.UiState.Success
import com.example.wordsfactory.ui.theme.Primary
import org.koin.androidx.compose.koinViewModel

@Composable
fun DictionaryScreen(viewModel: DictionaryViewModel = koinViewModel()) {
    val dictionaryUiState by viewModel.dictionaryUiState.collectAsStateWithLifecycle()
    val dictionaryState by viewModel.dictionaryState.collectAsStateWithLifecycle()

//    val content = listOf(
//        WordContent(
//            "Word",
//            "[transcription]",
//            "Voice",
//            "Part of speech",
//            listOf(
//                Meaning(
//                    "Definition",
//                    "Example"
//                ),
//                Meaning(
//                    "Another DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother Definition",
//                    "Example"
//                ),
//            )
//        )
//    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        SearchRow(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
            search = dictionaryState.searchText,
            onSearchValueChange = { viewModel.onSearchTextChanged(it) },
            onSearch = {})
        when (dictionaryUiState) {
            is Loading -> DictionaryScreenLoading()
            is Default -> DictionaryScreenPlaceholder(modifier = Modifier.padding(top = 52.dp))
            is Error -> DictionaryScreenPlaceholder(modifier = Modifier.padding(top = 52.dp))
            is Success -> if (dictionaryState.wordContent != null) {
                DictionaryScreenContent(
                    modifier = Modifier.padding(top = 16.dp),
                    wordContent = dictionaryState.wordContent!!,
                    viewModel = viewModel
                )
            } else DictionaryScreenPlaceholder(
                modifier = Modifier.padding(top = 52.dp)
            )
        }
    }
}

@Composable
fun DictionaryScreenLoading() {
    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        color = Primary,
        trackColor = Primary.copy(alpha = 0.2f)
    )
}


data class WordContent(
    val word: String,
    val transcription: String,
    val voice: String?,
    val partOfSpeech: String,
    val meanings: List<Meaning>
)

data class Meaning(
    val definition: String,
    val example: String?,
)

@Preview(showBackground = true)
@Composable
private fun DictPlaceholderPrev() {
    DictionaryScreen()
}