package com.example.wordsfactory.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.wordsfactory.R

val Typography = Typography(
    displayLarge = TextStyle(
        fontSize = 56.sp,
        lineHeight = 58.8.sp,
        fontFamily = FontFamily(Font(R.font.rubik_bold)),
        fontWeight = FontWeight(700),
        color = TextColor,
    ),
    displayMedium = TextStyle(
        fontSize = 40.sp,
        lineHeight = 46.sp,
        fontFamily = FontFamily(Font(R.font.rubik_bold)),
        fontWeight = FontWeight(700),
        color = TextColor,
    ),
    displaySmall = TextStyle(
        fontSize = 32.sp,
        lineHeight = 42.sp,
        fontFamily = FontFamily(Font(R.font.rubik_bold)),
        fontWeight = FontWeight(700),
        color = TextColor,
    ),
    headlineLarge = TextStyle(
        fontSize = 24.sp,
        lineHeight = 32.sp,
        fontFamily = FontFamily(Font(R.font.rubik_medium)),
        fontWeight = FontWeight(500),
        color = TextColor,
    ),
    headlineMedium = TextStyle(
        fontSize = 20.sp,
        lineHeight = 26.sp,
        fontFamily = FontFamily(Font(R.font.rubik_medium)),
        fontWeight = FontWeight(500),
        color = TextColor,
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        lineHeight = 26.sp,
        fontFamily = FontFamily(Font(R.font.rubik_regular)),
        fontWeight = FontWeight(400),
        color = TextColor,
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        lineHeight = 21.sp,
        fontFamily = FontFamily(Font(R.font.rubik_regular)),
        fontWeight = FontWeight(400),
        color = TextColor,
    ),
    bodySmall = TextStyle(
        fontSize = 12.sp,
        lineHeight = 18.sp,
        fontFamily = FontFamily(Font(R.font.rubik_medium)),
        fontWeight = FontWeight(400),
        color = TextColor,
    ),
    labelLarge = TextStyle(
        fontSize = 18.sp,
        lineHeight = 22.sp,
        fontFamily = FontFamily(Font(R.font.rubik_medium)),
        fontWeight = FontWeight(500),
        color = TextColor,
    ),
    labelMedium = TextStyle(
        fontSize = 16.sp,
        lineHeight = 18.sp,
        fontFamily = FontFamily(Font(R.font.rubik_medium)),
        fontWeight = FontWeight(500),
        color = TextColor,
    ),
    labelSmall = TextStyle(
        fontSize = 14.sp,
        lineHeight = 16.sp,
        fontFamily = FontFamily(Font(R.font.rubik_medium)),
        fontWeight = FontWeight(500),
        color = TextColor,
    ),
)