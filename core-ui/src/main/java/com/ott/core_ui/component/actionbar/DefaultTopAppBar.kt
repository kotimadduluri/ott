package com.ott.core_ui.component.actionbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import com.ott.core_ui.util.UiText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopAppBar(
    title: UiText,
    scrollBehaviour: TopAppBarScrollBehavior,
    navigationIcon: @Composable () -> Unit = { DefaultNavigationIcon {} },
    toolbarActions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(
                text = title.asString(),
                style = MaterialTheme.typography.headlineMedium
            )
        },
        navigationIcon = navigationIcon,
        actions = toolbarActions,
        scrollBehavior = scrollBehaviour
    )
}