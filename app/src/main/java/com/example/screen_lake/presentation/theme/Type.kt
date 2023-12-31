package com.example.screen_lake.presentation.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.screen_lake.R

val appFonts = FontFamily(
    Font(R.font.inter_tight_regular),
)

// Set of Material typography styles to start with
val ScreenLakeTypography = Typography(
    h1 = TextStyle(
        fontWeight = FontWeight(500),
        fontFamily = appFonts,
        fontSize = 28.sp,
    ),
    h2 = TextStyle(
        fontWeight = FontWeight(500),
        fontFamily = appFonts,
        fontSize = 16.sp,
    ),
    h3 = TextStyle(
        fontWeight = FontWeight(400),
        fontFamily = appFonts,
        fontSize = 16.sp,
    ),
    subtitle2 = TextStyle(
        fontWeight =  FontWeight(500),
        fontFamily = appFonts,
        fontSize = 14.sp,
    ),
    body1 =  TextStyle(
        fontWeight =  FontWeight(400),
        fontFamily = appFonts,
        fontSize = 14.sp,
    ),
    body2 = TextStyle(
        fontWeight =  FontWeight(400),
        fontFamily = appFonts,
        fontSize = 12.sp,
    ),
    button=TextStyle(
        fontWeight =  FontWeight(600),
        fontFamily = appFonts,
        fontSize = 16.sp,
    )

    )