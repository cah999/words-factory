package com.example.wordsfactory.presentation.ui.intro

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wordsfactory.R
import com.example.wordsfactory.presentation.ui.intro.utils.CustomIndicator
import com.example.wordsfactory.presentation.ui.intro.utils.IntroPage
import com.example.wordsfactory.presentation.ui.utils.AccentButton
import com.example.wordsfactory.ui.theme.Dark
import com.example.wordsfactory.ui.theme.DarkGrey
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IntroScreen(onNavigation: () -> Unit) {
    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.w("INTRO", "Fetching FCM registration token failed", task.exception)
            return@OnCompleteListener
        }

        // Get new FCM registration token
        val token = task.result

        // Log and toast
        Log.d("INTRO", token)
    })
    val pages = listOf(
        IntroPage(
            R.drawable.intro1,
            stringResource(R.string.intro_1_title),
            stringResource(R.string.intro_desc)
        ), IntroPage(
            R.drawable.intro2,
            stringResource(R.string.intro_2_title),
            stringResource(R.string.intro_desc)
        ), IntroPage(
            R.drawable.intro3,
            stringResource(R.string.intro_3_title),
            stringResource(R.string.intro_desc)
        )
    )
    val pagerState = rememberPagerState(pageCount = {
        3
    })
    val coroutineScope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp)
    ) {
        Text(
            text = stringResource(R.string.skip),
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 16.dp)
                .clickable { onNavigation() },
            style = MaterialTheme.typography.labelMedium,
            color = DarkGrey
        )
        Spacer(modifier = Modifier.height(5.dp))
        HorizontalPager(state = pagerState) { page ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = pages[page].image), contentDescription = null,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Box(Modifier.height(72.dp)) {
                    Text(
                        text = pages[page].title,
                        style = MaterialTheme.typography.headlineLarge,
                        color = Dark,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = pages[page].description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = DarkGrey
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        CustomIndicator(
            fullWidth = 60.dp,
            count = 3,
            pagerState = pagerState,
            radius = CornerRadius(16f),
            circleSpacing = 12.dp,
            activeLineWidth = 16.dp,
            width = 6.dp,
            height = 6.dp
        )
        Spacer(modifier = Modifier.weight(1f))
        AccentButton(
            onClick = {
                if (pagerState.currentPage == 2) {
                    onNavigation()
                } else coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
            },
            isEnabled = true,
            text = if (pagerState.currentPage == 2) stringResource(R.string.lets_start) else stringResource(
                R.string.next
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}


@Preview
@Composable
private fun IntroPreview() {
    IntroScreen(onNavigation = {})
}