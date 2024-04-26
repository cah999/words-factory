package com.example.wordsfactory.presentation.ui.question.elements

import androidx.compose.animation.core.LinearEasing
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wordsfactory.domain.model.Answer
import com.example.wordsfactory.ui.theme.Dark
import com.example.wordsfactory.ui.theme.Error
import com.example.wordsfactory.ui.theme.Primary
import com.example.wordsfactory.ui.theme.PrimaryLight
import com.example.wordsfactory.ui.theme.Yellow

@Composable
fun AnswerBox(
    modifier: Modifier = Modifier,
    answer: Answer,
    answerVariant: String,
    buttonClicked: Boolean = false,
    onClick: () -> Unit = {},
    afterClick: () -> Unit = {}
) {

    var clicked by remember { mutableStateOf(false) }
    Box(
        contentAlignment = Alignment.CenterStart, modifier = modifier
            .fillMaxWidth()
            .animatedBorder(borderColors = listOf(Yellow, Primary, Error),
                backgroundColor = PrimaryLight,
                shape = RoundedCornerShape(16.dp),
                borderWidth = 3.dp,
                easing = LinearEasing,
                animationDurationInMillis = 1000,
                clicked = clicked,
                onClick = {
                    afterClick()
                    clicked = false
                })
            .clickable(onClick = {
                if (!buttonClicked) {
                    onClick()
                    clicked = true
                }
            })
    ) {
        Row(Modifier.padding(24.dp)) {
            Text(
                text = "$answerVariant.", style = MaterialTheme.typography.bodyMedium, color = Dark
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = answer.text, style = MaterialTheme.typography.bodyMedium, color = Dark
            )
        }
    }
}