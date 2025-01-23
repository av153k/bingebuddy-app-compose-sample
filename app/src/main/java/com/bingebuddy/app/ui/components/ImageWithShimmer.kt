package com.bingebuddy.app.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent

@Composable
fun ImageWithShimmer(imageUrl: String) {
    SubcomposeAsyncImage(
        model = imageUrl,
        contentDescription = "Image with shimmer",
        modifier = Modifier.clip(RoundedCornerShape(8.dp)),
        loading = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .shimmerLoading()
            )
        },
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
