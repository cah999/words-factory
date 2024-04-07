package com.example.wordsfactory.presentation.ui.dictionary

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wordsfactory.R
import com.example.wordsfactory.presentation.ui.utils.AccentButton
import com.example.wordsfactory.ui.theme.Dark
import com.example.wordsfactory.ui.theme.DarkGrey
import com.example.wordsfactory.ui.theme.Primary
import kotlinx.coroutines.launch

@Composable
fun DictionaryScreenContent(
    modifier: Modifier = Modifier, wordContent: WordContent, viewModel: DictionaryViewModel
) {
    val dictionaryState by viewModel.dictionaryState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val mediaPlayer = remember { MediaPlayer() }

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
                modifier = Modifier.padding(top = 5.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            if (wordContent.voice?.isNotEmpty() == true) {
                IconButton(modifier = Modifier.size(25.dp, 22.dp), onClick = {
                    coroutineScope.launch {
                        viewModel.onAudioLoading(true)
                        mediaPlayer.reset()
                        mediaPlayer.setDataSource(wordContent.voice)
                        mediaPlayer.setOnPreparedListener {
                            it.start()
                            viewModel.onAudioLoading(false)
                        }
                        mediaPlayer.setOnErrorListener { _, _, _ ->
                            viewModel.onAudioLoading(false)
                            true
                        }
                        mediaPlayer.prepareAsync()
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.voice),
                        contentDescription = "",
                        tint = Primary,
                        modifier = Modifier.size(25.dp, 22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                if (dictionaryState.isAudioLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp, 20.dp),
                        color = Primary,
                        trackColor = Primary.copy(alpha = 0.2f)
                    )
                }
            }
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