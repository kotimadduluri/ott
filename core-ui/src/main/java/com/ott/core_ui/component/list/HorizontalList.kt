package com.ott.core_ui.component.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.ott.core_ui.theme.OttTheme

@Composable
fun <T> HorizontalList(
    data: List<T>,
    modifier: Modifier = Modifier.padding(OttTheme.Dimens.sm),
    arrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(OttTheme.Dimens.sm),
    content: @Composable (index: Int, T) -> Unit
) {
    LazyRow(
        modifier = modifier.testTag("HorizontalList"),
        horizontalArrangement = arrangement,
    ) {
        itemsIndexed(data) { index, item ->
            content(index, item)
        }
    }
}