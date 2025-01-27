package com.bingebuddy.app.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent

@Composable
fun ImageWithShimmer(
    imageUrl: String,
    modifier: Modifier = Modifier.clip(RoundedCornerShape(8.dp)),
    contentScale: ContentScale = ContentScale.Fit,
) {
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
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Failed to load image",
                    modifier = Modifier.align(androidx.compose.ui.Alignment.Center)
                )
            }
        },
        success = {
            SubcomposeAsyncImageContent()
        }
    )
}
