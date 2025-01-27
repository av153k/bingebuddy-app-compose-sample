package com.bingebuddy.app.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BrokenImage
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import java.lang.StringBuilder

@Composable
fun ImageWithShimmer(
    imageUrl: String,
    modifier: Modifier = Modifier.clip(RoundedCornerShape(8.dp)),
    contentScale: ContentScale = ContentScale.Fit,
    errorBuilder: @Composable () -> Unit = {
        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {
            Icon(Icons.Outlined.BrokenImage, contentDescription = "broken-image")
        }
    }

) {
    Box(modifier) {
        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = "Image with shimmer",
            modifier = modifier,
            loading = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .shimmerLoading()
                )
            },
            contentScale = contentScale,
            error = {
                errorBuilder()
            },
            success = {
                SubcomposeAsyncImageContent()
            }
        )
    }
}
