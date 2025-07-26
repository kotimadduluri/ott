package com.ott.core_ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.ott.core_ui.component.actionbar.DefaultTopAppBar
import com.ott.core_ui.component.actionbar.DefaultNavigationIcon
import com.ott.core_ui.util.UiText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContainer(
    title: UiText = UiText.PlainString("Back"),
    scrollBehaviour: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    navigation: @Composable () -> Unit = { DefaultNavigationIcon {} },
    toolbarActions: @Composable RowScope.() -> Unit = {},
    toolbar: @Composable () -> Unit = {
        DefaultTopAppBar(
            title = title,
            scrollBehaviour = scrollBehaviour,
            navigationIcon = navigation,
            toolbarActions = toolbarActions
        )
    },
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = toolbar
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            content()
        }
    }
}
