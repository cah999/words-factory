package com.example.wordsfactory.presentation.ui.dictionary

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wordsfactory.R
import com.example.wordsfactory.presentation.ui.utils.AccentButton
import com.example.wordsfactory.ui.theme.Dark
import com.example.wordsfactory.ui.theme.DarkGrey
import com.example.wordsfactory.ui.theme.Grey
import com.example.wordsfactory.ui.theme.Primary
import com.example.wordsfactory.ui.theme.Secondary

@Composable
fun DictionaryScreen() {
    val content = listOf(
        WordContent(
            "Word",
            "[transcription]",
            "Voice",
            "Part of speech",
            listOf(
                Meaning(
                    "Definition",
                    "Example"
                ),
                Meaning(
                    "Another DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother DefinitionAnother Definition",
                    "Example"
                ),
            )
        )
    )
//    val content = emptyList<WordContent>()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        SearchRow(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
            search = "",
            onSearchValueChange = {},
            onSearch = {})
        if (content.isNotEmpty()) {
            DictionaryScreenContent(
                modifier = Modifier.padding(top = 16.dp), wordContent = content.first()
            )
        } else DictionaryScreenPlaceholder(
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun DictionaryScreenContent(
    modifier: Modifier = Modifier, wordContent: WordContent
) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = wordContent.word,
                style = MaterialTheme.typography.headlineLarge,
                color = Dark
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = wordContent.transcription,
                style = MaterialTheme.typography.bodyMedium,
                color = Primary,
                modifier = Modifier.align(Alignment.Bottom)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                painter = painterResource(id = R.drawable.voice),
                contentDescription = "",
                tint = Primary
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.part_of_speech),
                style = MaterialTheme.typography.headlineMedium,
                color = Dark
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = wordContent.partOfSpeech,
                style = MaterialTheme.typography.bodyMedium,
                color = DarkGrey
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.meanings),
            style = MaterialTheme.typography.headlineMedium,
            color = Dark
        )
        Spacer(modifier = Modifier.height(10.dp))
        wordContent.meanings.forEach {
            MeaningBox(
                modifier = Modifier.fillMaxWidth(), meaning = it
            )
            if (it != wordContent.meanings.last()) Spacer(modifier = Modifier.height(8.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        AccentButton(
            modifier = Modifier.padding(horizontal = 10.dp),
            onClick = {},
            isEnabled = true,
            text = stringResource(R.string.add_to_dictionary)
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun MeaningBox(modifier: Modifier, meaning: Meaning) {
    Box(modifier = modifier.border(1.dp, Grey, RoundedCornerShape(16.dp))) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = meaning.definition, style = MaterialTheme.typography.bodyMedium, color = Dark
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(R.string.example),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Secondary
                )
                Text(
                    text = meaning.example,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Dark
                )
            }
        }
    }
}


data class WordContent(
    val word: String,
    val transcription: String,
    val voice: String,
    val partOfSpeech: String,
    val meanings: List<Meaning>
)

data class Meaning(
    val definition: String,
    val example: String,
)

@Composable
fun DictionaryScreenPlaceholder(modifier: Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.dictionary),
            contentDescription = null,
            modifier = Modifier.height(212.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(R.string.no_word),
            style = MaterialTheme.typography.headlineLarge,
            color = Dark
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.input_something),
            style = MaterialTheme.typography.bodyMedium,
            color = DarkGrey
        )
    }
}

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

@Preview(showBackground = true)
@Composable
private fun DictPlaceholderPrev() {
    DictionaryScreen()
}

//@Preview
//@Composable
//private fun SearchRowPrev() {
//    val search = remember { mutableStateOf("") }
//    SearchRow(search = search.value, onSearchValueChange = { search.value = it }, onSearch = {})
//}