package com.example.wordsfactory.presentation.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.TypedValue
import androidx.annotation.FontRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import com.example.wordsfactory.R
import com.example.wordsfactory.ui.theme.DarkGrey
import com.example.wordsfactory.ui.theme.White
import org.koin.compose.koinInject

class MyAppWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            MyAppWidgetContent()
        }
    }

    @Composable
    fun MyAppWidgetContent() {

        val viewModel = koinInject<WidgetViewModel>()
        val state by viewModel.widgetState.collectAsState()
        LaunchedEffect(Unit) {
            viewModel.updateMyWords()
            viewModel.updateMyWordsRemembered()
        }
        Column(modifier = GlanceModifier.fillMaxSize().background(White)) {
            Row(
                verticalAlignment = Alignment.Vertical.CenterVertically,
                horizontalAlignment = Alignment.Horizontal.Start,
                modifier = GlanceModifier.fillMaxWidth()
                    .background(ImageProvider(R.drawable.icon_background)).height(40.dp)
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                GlanceText(
                    text = "WordsFactory",
                    letterSpacing = 0.sp,
                    font = R.font.rubik_bold,
                    fontSize = 24.sp,
                    color = White
                )
                Spacer(GlanceModifier.defaultWeight())
                Image(
                    provider = ImageProvider(R.drawable.refresh),
                    contentDescription = null,
                    modifier = GlanceModifier.clickable(
                        onClick = actionRunCallback<RefreshAction>()
                    ).size(14.dp)
                )
            }
            Spacer(GlanceModifier.height(8.dp))
            Row(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalAlignment = Alignment.CenterVertically,
                modifier = GlanceModifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp)
            ) {
                GlanceText(
                    letterSpacing = 0.sp,
                    text = "My dictionary",
                    font = R.font.rubik_medium,
                    fontSize = 15.sp,
                )
                Spacer(GlanceModifier.defaultWeight())
                GlanceText(
                    letterSpacing = 0.sp,
                    text = "${state.myWords} Words",
                    font = R.font.rubik_regular,
                    fontSize = 10.sp,
                    color = DarkGrey
                )
            }
            Spacer(GlanceModifier.height(10.dp))
            Row(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalAlignment = Alignment.CenterVertically,
                modifier = GlanceModifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp)
            ) {
                GlanceText(
                    letterSpacing = 0.sp,
                    text = "I already remember",
                    font = R.font.rubik_medium,
                    fontSize = 15.sp,
                )
                Spacer(GlanceModifier.defaultWeight())
                GlanceText(
                    letterSpacing = 0.sp,
                    text = "${state.myWordsRemembered} Words",
                    font = R.font.rubik_regular,
                    fontSize = 10.sp,
                    color = DarkGrey
                )
            }
        }
    }

    private fun Context.textAsBitmap(
        text: String,
        fontSize: TextUnit,
        color: Color = Color.Black,
        letterSpacing: Float = 0f,
        font: Int
    ): Bitmap {
        val paint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        paint.textSize = spToPx(fontSize.value, this).toFloat()
        paint.color = color.toArgb()
        paint.letterSpacing = letterSpacing
        paint.typeface = ResourcesCompat.getFont(this, font)

        val baseline = -paint.ascent()
        val width = (paint.measureText(text)).toInt()
        val height = (baseline + paint.descent()).toInt()
        val image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(image)
        canvas.drawText(text, 0f, baseline, paint)
        return image
    }

    private fun spToPx(sp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, sp, context.resources.displayMetrics
        ).toInt()
    }

    @Composable
    fun GlanceText(
        text: String,
        @FontRes font: Int,
        fontSize: TextUnit,
        modifier: GlanceModifier = GlanceModifier,
        color: Color = Color.Black,
        letterSpacing: TextUnit = 0.1.sp
    ) {
        Image(
            modifier = modifier,
            provider = ImageProvider(
                LocalContext.current.textAsBitmap(
                    text = text,
                    fontSize = fontSize,
                    color = color,
                    font = font,
                    letterSpacing = letterSpacing.value
                )
            ),
            contentDescription = null,
        )
    }
}


