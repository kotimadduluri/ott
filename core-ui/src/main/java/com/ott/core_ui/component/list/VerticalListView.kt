package com.ott.core_ui.component.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ott.core_ui.theme.OttTheme

@Composable
fun <T> VerticalList(
    data: List<T>,
    modifier: Modifier = Modifier.padding(OttTheme.Dimens.sm),
    verticalArrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(OttTheme.Dimens.sm),
    itemContent: @Composable (Int,T) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = verticalArrangement
    ) {
        itemsIndexed(data) {index,item ->
            itemContent(index,item)
        }
    }
}