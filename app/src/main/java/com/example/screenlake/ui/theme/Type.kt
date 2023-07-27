package com.example.screenlake.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.screenlake.R

private val appFonts = FontFamily(
//    Font(R.font.inter_tight_bold, weight = FontWeight.Bold),
//    Font(R.font.inter_tight_extra_bold, weight = FontWeight.ExtraBold),
//    Font(R.font.inter_tight_light, weight = FontWeight.Light),
//    Font(R.font.inter_tight_medium, weight = FontWeight.Medium),
//    Font(R.font.inter_tight_semi_bold, weight = FontWeight.SemiBold),
    Font(R.font.inter_tight_regular),
)

// Set of Material typography styles to start with
val ScreenLakeTypography = Typography(
    displayLarge = TextStyle(
        fontWeight = FontWeight(500),
        fontFamily = appFonts,
        fontSize = 28.sp,
    ),
//    displayMedium = TextStyle(
//        fontWeight = FontWeight(400),
//        fontFamily = appFonts,
//        fontSize = 16.sp,
//    ),
//    displaySmall = TextStyle(
//        fontWeight = FontWeight.Normal,
//        fontFamily = appFonts,
//        fontSize = 57.sp,
//    ),
//    headlineLarge = TextStyle(
//        fontWeight = FontWeight.Normal,
//        fontFamily = appFonts,
//        fontSize = 57.sp,
//    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight(400),
        fontFamily = appFonts,
        fontSize = 16.sp,
    ),
//    headlineSmall = TextStyle(
//        fontWeight = FontWeight.Normal,
//        fontFamily = appFonts,
//        fontSize = 57.sp,
//    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight(500),
        fontFamily = appFonts,
        fontSize = 16.sp,
    ),
    titleMedium = TextStyle(
        fontWeight =  FontWeight(400),
        fontFamily = appFonts,
        fontSize = 14.sp,
    ),
//    titleSmall = TextStyle(
//        fontWeight = FontWeight.Normal,
//        fontFamily = appFonts,
//        fontSize = 57.sp,
//    ),
//    bodyLarge = TextStyle(
//        fontWeight = FontWeight.Normal,
//        fontFamily = appFonts,
//        fontSize = 57.sp,
//    ),
//    bodyMedium = TextStyle(
//        fontWeight = FontWeight.Normal,
//        fontFamily = appFonts,
//        fontSize = 57.sp,
//    ),
//    bodySmall = TextStyle(
//        fontWeight = FontWeight.Normal,
//        fontFamily = appFonts,
//        fontSize = 57.sp,
//    ),
//    labelLarge = TextStyle(
//        fontWeight = FontWeight.Normal,
//        fontFamily = appFonts,
//        fontSize = 57.sp,
//    ),
//    labelMedium = TextStyle(
//        fontWeight = FontWeight.Normal,
//        fontFamily = appFonts,
//        fontSize = 57.sp,
//    ),
//    labelSmall = TextStyle(
//        fontWeight = FontWeight.Normal,
//        fontFamily = appFonts,
//        fontSize = 57.sp,
//    ),

)