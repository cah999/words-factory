package com.example.wordsfactory.presentation.ui.dictionary.elements

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wordsfactory.R
import com.example.wordsfactory.domain.model.Definition
import com.example.wordsfactory.ui.theme.Dark
import com.example.wordsfactory.ui.theme.Grey
import com.example.wordsfactory.ui.theme.Secondary

@Composable
fun MeaningBox(modifier: Modifier, definition: Definition) {
    Box(modifier = modifier.border(1.dp, Grey, RoundedCornerShape(16.dp))) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = definition.definition,
                style = MaterialTheme.typography.bodyMedium,
                color = Dark
            )
            if (definition.example != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.example),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Secondary
                    )
                    Text(
                        text = definition.example,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Dark
                    )
                }
            }
        }
    }
}