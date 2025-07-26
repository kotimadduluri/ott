package com.ott.core_ui.component.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ott.core_ui.theme.OttTheme

@Composable
fun CardView(
    modifier: Modifier = Modifier,
    onClicked: (() -> Unit)? = null,
    itemContent: @Composable () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClicked?.invoke() },
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(OttTheme.Dimens.elevMd)
    ) {
        Column(modifier = modifier.padding(OttTheme.Dimens.xs)) {
            itemContent()
        }
    }
}