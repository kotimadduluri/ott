package com.ott.core_ui.component.actionbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.ott.core_ui.util.UiText

@Composable
fun DefaultNavigationIcon(
    contentDescription : UiText = UiText.PlainString("Nav"),
    onclick: () -> Unit
) = IconButton(onClick = onclick) {
    Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = contentDescription.asString()
    )
}