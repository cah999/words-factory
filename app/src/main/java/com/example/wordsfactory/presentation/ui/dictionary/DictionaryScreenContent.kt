package com.example.wordsfactory.presentation.ui.dictionary

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wordsfactory.R
import com.example.wordsfactory.domain.model.WordContent
import com.example.wordsfactory.presentation.ui.dictionary.elements.MeaningBox
import com.example.wordsfactory.ui.theme.Dark
import com.example.wordsfactory.ui.theme.DarkGrey
import com.example.wordsfactory.ui.theme.Grey
import com.example.wordsfactory.ui.theme.Primary
import com.example.wordsfactory.ui.theme.White
import kotlinx.coroutines.launch
import java.util.Locale


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DictionaryScreenContent(
    modifier: Modifier = Modifier, wordContents: List<WordContent>, viewModel: DictionaryViewModel
) {
    val dictionaryState by viewModel.dictionaryState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val mediaPlayer = remember { MediaPlayer() }
    val pagerState = rememberPagerState(pageCount = { wordContents.size })

    LaunchedEffect(pagerState.currentPage) {
        viewModel.onCurrentPageChanged(pagerState.currentPage)
    }
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        pageSpacing = 16.dp,
        verticalAlignment = Alignment.Top
    ) { page ->
        val phoneticsVariant = dictionaryState.phoneticsVariants[page] ?: 0
        val partOfSpeechVariant = dictionaryState.partOfSpeechVariants[page] ?: 0
        val wordContent = wordContents[page]
        Column(modifier = modifier) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = wordContent.word.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.getDefault()
                        ) else it.toString()
                    }, style = MaterialTheme.typography.headlineLarge, color = Dark
                )
                if (wordContent.phonetics?.isNotEmpty() == true) {
                    val voice = wordContent.phonetics[phoneticsVariant].voice
                    if (wordContent.phonetics.isNotEmpty()) {
                        Spacer(modifier = Modifier.width(16.dp))
                        if (wordContent.phonetics.size == 1) {
                            Log.d(
                                "DictionaryScreenContent",
                                "transcription: ${wordContent.phonetics[0].transcription}"
                            )
                            Text(
                                text = wordContent.phonetics[0].transcription ?: "",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Primary,
                                modifier = Modifier.padding(top = 5.dp)
                            )
                        } else if (wordContent.phonetics.size > 1) {
                            LazyRow(
                                modifier = Modifier
                                    .weight(1f, false)
                                    .wrapContentWidth(align = Alignment.Start)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(1.dp, Grey, RoundedCornerShape(8.dp))
                            ) {
                                items(wordContent.phonetics.size) { index ->
                                    Box(modifier = Modifier
                                        .padding(4.dp)
                                        .clickable {
                                            viewModel.onPhoneticsVariantChanged(page, index)
                                        }
                                        .background(
                                            color = if (phoneticsVariant == index) Primary else Color.Transparent,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .padding(horizontal = 8.dp, vertical = 4.dp)) {
                                        // todo фишка (если не пришёл текст, то пишется тип транскрипции)
                                        Text(
                                            text = wordContent.phonetics[index].transcription
                                                ?: viewModel.getTranscriptionType(
                                                    wordContent.phonetics[index]
                                                ),
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = if (phoneticsVariant == index) White else DarkGrey
                                        )
                                    }

                                }
                            }
                        }
                        if (!voice.isNullOrBlank()) {
                            Spacer(modifier = Modifier.width(16.dp))
                            IconButton(modifier = Modifier.size(25.dp, 22.dp), onClick = {
                                coroutineScope.launch {
                                    viewModel.onAudioLoading(true)
                                    mediaPlayer.reset()
                                    mediaPlayer.setDataSource(voice)
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
                }
            }
            if (wordContent.meanings[partOfSpeechVariant].partOfSpeech.isNotBlank()) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.part_of_speech),
                        style = MaterialTheme.typography.headlineMedium,
                        color = Dark
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = wordContent.meanings[partOfSpeechVariant].partOfSpeech,
                        style = MaterialTheme.typography.bodyMedium,
                        color = DarkGrey,
                        modifier = if (wordContent.meanings.size > 1) Modifier.clickable {
                            viewModel.onDropDownExpanded(
                                page, true
                            )
                        } else Modifier)
                    if (wordContent.meanings.size > 1) {
                        Box(contentAlignment = Alignment.CenterStart) {
                            Spacer(modifier = Modifier.width(4.dp))
                            IconButton(
                                onClick = { viewModel.onDropDownExpanded(page, true) },
                                modifier = Modifier
                                    .size(16.dp)
                                    .padding(top = 2.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.triangle),
                                    contentDescription = "",
                                    tint = Primary,
                                )
                            }

                            DropdownMenu(
                                expanded = dictionaryState.dropDownExpanded[page] ?: false,
                                modifier = Modifier.background(White),
                                onDismissRequest = {
                                    viewModel.onDropDownExpanded(
                                        page,
                                        false
                                    )
                                },
                            ) {
                                wordContent.meanings.forEach { meaning ->
                                    DropdownMenuItem(text = {
                                        Text(
                                            text = meaning.partOfSpeech,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = DarkGrey
                                        )
                                    }, onClick = {
                                        viewModel.onDropDownExpanded(page, false)
                                        viewModel.onPartOfSpeechVariantChanged(
                                            page,
                                            wordContent.meanings.indexOf(meaning),
                                        )
                                    })
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.meanings),
                style = MaterialTheme.typography.headlineMedium,
                color = Dark
            )
            Spacer(modifier = Modifier.height(10.dp))
            wordContent.meanings[partOfSpeechVariant].definitions.forEach {
                MeaningBox(
                    modifier = Modifier.fillMaxWidth(), definition = it
                )
                if (it != wordContent.meanings[partOfSpeechVariant].definitions.last()) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}