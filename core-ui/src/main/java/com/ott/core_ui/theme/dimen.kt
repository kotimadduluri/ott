package com.ott.core_ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp


data class AppDimens(
    // Spacing
    val xxs: Dp = 2.dp,
    val xs: Dp = 4.dp,
    val sm: Dp = 8.dp,
    val md: Dp = 16.dp,
    val lg: Dp = 24.dp,
    val xl: Dp = 32.dp,
    val xxl: Dp = 40.dp,

    // Font Sizes
    val fontSm: TextUnit = 12.sp,
    val fontMd: TextUnit = 16.sp,
    val fontLg: TextUnit = 20.sp,
    val fontXl: TextUnit = 24.sp,

    // Radius
    val radiusSm: Dp = 4.dp,
    val radiusMd: Dp = 8.dp,
    val radiusLg: Dp = 16.dp,

    // Elevation
    val elevLow: Dp = 2.dp,
    val elevMd: Dp = 6.dp,
    val elevHigh: Dp = 12.dp,

    // Icon sizes
    val iconSm: Dp = 16.dp,
    val iconMd: Dp = 24.dp,
    val iconLg: Dp = 32.dp,
)

val SmallScreenDimens = AppDimens(
    xxs = 1.dp,
    xs = 2.dp,
    sm = 4.dp,
    md = 8.dp,
    lg = 12.dp,
    xl = 16.dp,
    xxl = 20.dp,

    fontSm = 10.sp,
    fontMd = 14.sp,
    fontLg = 18.sp,
    fontXl = 20.sp,

    radiusSm = 2.dp,
    radiusMd = 4.dp,
    radiusLg = 8.dp,

    elevLow = 1.dp,
    elevMd = 3.dp,
    elevHigh = 6.dp,

    iconSm = 12.dp,
    iconMd = 16.dp,
    iconLg = 20.dp,
)

val LargeScreenDimens = AppDimens(
    xxs = 4.dp,
    xs = 8.dp,
    sm = 12.dp,
    md = 24.dp,
    lg = 36.dp,
    xl = 48.dp,
    xxl = 64.dp,

    fontSm = 14.sp,
    fontMd = 18.sp,
    fontLg = 22.sp,
    fontXl = 28.sp,

    radiusSm = 6.dp,
    radiusMd = 12.dp,
    radiusLg = 24.dp,

    elevLow = 3.dp,
    elevMd = 9.dp,
    elevHigh = 18.dp,

    iconSm = 20.dp,
    iconMd = 32.dp,
    iconLg = 48.dp,
)



val LocalDimens = staticCompositionLocalOf { AppDimens() }