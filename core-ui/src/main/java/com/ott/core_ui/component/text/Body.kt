package com.ott.core_ui.component.text

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.common.util.UiImage
import com.ott.core_ui.theme.OttTheme
import com.ott.core_ui.util.UiText

@Composable
fun Body(
    text: UiText,
    icon: UiImage? = null,
    fontColor: Color = MaterialTheme.colorScheme.onSurface,
    maxLines : Int = Int.MAX_VALUE,
    overflow : TextOverflow = TextOverflow.Ellipsis,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
) {
    Row {
        icon?.let {
            Icon(
                painter = icon.asPainter(),
                contentDescription = null,
                modifier = Modifier,
                tint = fontColor
            )
        }
        Text(
            text = text.asString(),
            modifier = modifier,
            color = fontColor,
            style = textStyle,
            maxLines = maxLines,
            overflow = overflow
        )
    }
}

@Preview(showBackground = true)
@Composable()
private fun LabelPreview(){
    OttTheme {
        Body(
            text = UiText.PlainString("Testing...")
        )
    }
}