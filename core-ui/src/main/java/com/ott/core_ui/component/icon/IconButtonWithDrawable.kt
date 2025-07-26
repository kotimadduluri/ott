package com.ott.core_ui.component.icon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import com.common.util.UiImage
import com.ott.core_ui.theme.OttTheme
import com.ott.core_ui.util.UiText

@Composable
fun IconWithDrawable(
    icon: UiImage,
    tint:Color?=null,
    modifier: Modifier = Modifier,
    contentDescription: UiText? = null,
    onClick: () -> Unit = {}
) {
    IconButton(
        modifier = modifier
            .size(OttTheme.Dimens.lg)
            .testTag("IconButton_${contentDescription?.asString() ?: ""}"),
        onClick = onClick,
    ) {
        Image(
            painter = icon.asPainter(),
            contentDescription = contentDescription?.asString() ?: "icon",
            colorFilter = tint?.let { ColorFilter.tint(it) }
        )
    }
}