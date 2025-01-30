package com.bingebuddy.app.ui.components


import android.icu.text.DecimalFormat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bingebuddy.app.constants.StringConstants
import com.bingebuddy.app.data.network.model.DiscoverTvSeriesResultModel
import com.bingebuddy.app.data.toWatchlistItem


@Composable
fun DiscoverTvSeriesListCard(
    tvSeries: DiscoverTvSeriesResultModel,
    onTvSeriesClicked: (tvSeriesId: String) -> Unit,
    typeBadge: @Composable () -> Unit = {
        Box {}
    },
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(170.dp)
            .clickable {
                onTvSeriesClicked("${tvSeries.id}")
            }
    ) {
        Column(
            modifier = Modifier.padding(5.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                Modifier
                    .weight(5f)
            ) {
                ImageWithShimmer(
                    imageUrl = "${StringConstants.IMAGE_BASE_URL}${tvSeries.posterPath}",
                    modifier = Modifier

                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop,
                    errorBuilder = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Filled.BrokenImage,
                                contentDescription = null,
                                Modifier.size(50.dp)
                            )
                        }
                    }
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        typeBadge()
                        AddToWatchlistButton(item = tvSeries.toWatchlistItem())
                    }
                }

            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                "${tvSeries.name}",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                "First air - ${tvSeries.firstAirDate}",
                style = MaterialTheme.typography.labelMedium,
            )
            Spacer(modifier = Modifier.height(5.dp))
            RatingChip(tvSeries.voteAverage, tvSeries.voteCount)
        }
    }
}


@Composable
fun DiscoverTvSeriesListShimmerCard() {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(170.dp)
            .fillMaxHeight()
            .shimmerLoading(),

        ) {}
}