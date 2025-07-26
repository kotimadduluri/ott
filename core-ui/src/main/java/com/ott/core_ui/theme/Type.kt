package com.ott.core_ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ott.core_ui.R


val nunito = FontFamily(
    Font(R.font.nunito_regular, FontWeight.Normal),
    Font(R.font.nunito_medium, FontWeight.Medium),
    Font(R.font.nunito_semi_bold, FontWeight.SemiBold),
    Font(R.font.nunito_bold, FontWeight.Bold),
    Font(R.font.nunito_extra_bold, FontWeight.ExtraBold)
)
val Typography = Typography(
    displayLarge = TextStyle(fontFamily = nunito, fontSize = 57.sp, fontWeight = FontWeight.Bold),
    displayMedium = TextStyle(fontFamily = nunito, fontSize = 45.sp, fontWeight = FontWeight.Bold),
    displaySmall = TextStyle(fontFamily = nunito, fontSize = 36.sp, fontWeight = FontWeight.Bold),
    headlineLarge = TextStyle(
        fontFamily = nunito,
        fontSize = 32.sp,
        fontWeight = FontWeight.ExtraBold
    ),
    headlineMedium = TextStyle(
        fontFamily = nunito,
        fontSize = 28.sp,
        fontWeight = FontWeight.ExtraBold
    ),
    headlineSmall = TextStyle(
        fontFamily = nunito,
        fontSize = 24.sp,
        fontWeight = FontWeight.ExtraBold
    ),
    titleLarge = TextStyle(fontFamily = nunito, fontSize = 22.sp, fontWeight = FontWeight.Bold),
    titleMedium = TextStyle(fontFamily = nunito, fontSize = 18.sp, fontWeight = FontWeight.Bold),
    titleSmall = TextStyle(fontFamily = nunito, fontSize = 14.sp, fontWeight = FontWeight.Bold),
    bodyLarge = TextStyle(fontFamily = nunito, fontSize = 16.sp, fontWeight = FontWeight.Medium),
    bodyMedium = TextStyle(fontFamily = nunito, fontSize = 14.sp, fontWeight = FontWeight.Medium),
    bodySmall = TextStyle(fontFamily = nunito, fontSize = 12.sp, fontWeight = FontWeight.Medium),
    labelLarge = TextStyle(fontFamily = nunito, fontSize = 14.sp, fontWeight = FontWeight.Medium),
    labelMedium = TextStyle(fontFamily = nunito, fontSize = 12.sp, fontWeight = FontWeight.Medium),
    labelSmall = TextStyle(fontFamily = nunito, fontSize = 11.sp, fontWeight = FontWeight.Medium),
)