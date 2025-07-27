package com.ott.core_ui.component.state

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ott.core_ui.component.button.SimpleButton
import com.ott.core_ui.component.text.Body
import com.ott.core_ui.theme.OttTheme

@Composable
fun ActionStateView(
    isActionRequired: Boolean = false,
    action: ActionState,
    modifier: Modifier = Modifier
        .fillMaxSize()
        .padding(OttTheme.Dimens.md),
    block: () -> Unit = {}
) {
    Column(
        modifier = modifier.testTag("ActionStateView"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(OttTheme.Dimens.sm)
    ) {
        Image(
            painter = action.icon.asPainter(), // Replace with your error image resource
            contentDescription = action.title.asString(),
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(120.dp)
        )

        Body(
            text = action.message,
            fontColor = MaterialTheme.colorScheme.onBackground,
            textStyle = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onError, fontSize = 20.sp)
        )

        if (isActionRequired) {
            SimpleButton(action.title) {
                block()
            }
        }
    }
}

@Composable
@Preview
fun ActionStatePreview() {
    ActionStateView(
        action = ActionState.ERROR(),
        isActionRequired = false
    )
}
