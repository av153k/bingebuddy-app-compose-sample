package com.bingebuddy.app.ui.theme

import androidx.compose.material3.Typography

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.bingebuddy.app.R

val ralewayFontFamily = FontFamily(
    Font(R.font.josefin_sans_bold, weight = FontWeight.Bold),
    Font(R.font.josefin_sans_medium, weight = FontWeight.Medium),
    Font(R.font.josefin_sans_thin, weight = FontWeight.Thin),
    Font(R.font.josefin_sans_regular),
    Font(R.font.josefin_sans_light, weight = FontWeight.Light),
    Font(R.font.josefin_sans_extra_light, weight = FontWeight.ExtraLight),
    Font(R.font.josefin_sans_semi_bold, weight = FontWeight.SemiBold)
)

// Default Material 3 typography values
val baseline = Typography()

val BingeBuddyTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = ralewayFontFamily),
    displayMedium = baseline.displayMedium.copy(fontFamily = ralewayFontFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = ralewayFontFamily),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = ralewayFontFamily),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = ralewayFontFamily),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = ralewayFontFamily),
    titleLarge = baseline.titleLarge.copy(fontFamily = ralewayFontFamily),
    titleMedium = baseline.titleMedium.copy(fontFamily = ralewayFontFamily),
    titleSmall = baseline.titleSmall.copy(fontFamily = ralewayFontFamily),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = ralewayFontFamily),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = ralewayFontFamily),
    bodySmall = baseline.bodySmall.copy(fontFamily = ralewayFontFamily),
    labelLarge = baseline.labelLarge.copy(fontFamily = ralewayFontFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = ralewayFontFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = ralewayFontFamily),
)

