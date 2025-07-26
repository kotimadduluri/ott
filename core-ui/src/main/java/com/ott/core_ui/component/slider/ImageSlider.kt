package com.ott.core_ui.component.slider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.ott.core_ui.component.image.ImageView
import com.ott.core_ui.theme.OttTheme

@Composable
fun ImageSlider(images: List<String>) {
    val configuration = LocalConfiguration.current
    val (imageWidth, imageHeight) = remember(configuration) {
        configuration.screenWidthDp.dp to (configuration.screenHeightDp.dp / 2)
    }

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { images.size }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("ImageSlider"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            key = { images[it] },
            modifier = Modifier
                .width(imageWidth)
                .height(imageHeight)
        ) { page ->
            ImageView(
                url = images[page],
                contentDescription = "Image ${page + 1} of ${images.size}",
                modifier = Modifier.fillMaxSize()
            )
        }

        val currentPage = pagerState.currentPage

        Row(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            images.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(
                            if (index == currentPage) MaterialTheme.colorScheme.secondary
                            else Color.Gray
                        )
                )
                if (index != images.lastIndex) {
                    Spacer(modifier = Modifier.size(OttTheme.Dimens.xs))
                }
            }
        }
    }
}