package com.ott.core_ui.component.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.ott.core_ui.util.UiText

@Composable
fun Title(
    text: UiText,
    fontColor: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier = Modifier,
    maxLines : Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
) {
    Text(
        text = text.asString(),
        color = fontColor,
        style = textStyle,
        modifier = modifier,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Preview(showBackground = true)
@Composable
fun TitlePreview() {
    Title(text = UiText.PlainString("Sample Title"))
}