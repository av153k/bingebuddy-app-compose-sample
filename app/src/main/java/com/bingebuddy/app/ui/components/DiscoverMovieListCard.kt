package com.bingebuddy.app.ui.components

import android.icu.text.DecimalFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bingebuddy.app.constants.StringConstants
import com.bingebuddy.app.data.network.model.DiscoverMovieResultModel
import com.bingebuddy.app.data.toWatchlistItem
import com.bingebuddy.app.ui.screens.watchlist.WatchlistUiState
import com.bingebuddy.app.ui.screens.watchlist.WatchlistViewmodel
import com.bingebuddy.app.ui.theme.Dimensions


@Composable
fun DiscoverMovieListCard(
    movie: DiscoverMovieResultModel,
    typeBadge: @Composable () -> Unit = {
        Box {}
    },
    width: Dp = Dimensions.contentCardWidth,
    modifier: Modifier = Modifier,
    onClick: (movieId: String) -> Unit,
) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(width)
            .clickable {
                onClick("${movie.id}")
            }
    ) {
        Box {
            Column(
                modifier = Modifier.padding(5.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Box(
                    Modifier
                        .weight(5f)
                ) {
                    ImageWithShimmer(
                        imageUrl = "${StringConstants.IMAGE_BASE_URL}${movie.posterPath}",
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
                            AddToWatchlistButton(item = movie.toWatchlistItem())
                        }
                    }

                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    "${movie.title}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f, fill = false),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    "Release - ${movie.releaseDate}",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(5.dp))
                RatingChip(movie.voteAverage, movie.voteCount)
            }
            if (movie.adult == true) {
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .fillMaxWidth()
                        .background(Color.Red)
                        .align(Alignment.TopStart)// Offset for better positioning
                ) {
                    Text(
                        text = "Adult(18+)",
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(5.dp)
                    )
                }
            }


        }
    }
}


@Composable
fun DiscoverMovieListShimmerCard() {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(170.dp)
            .fillMaxHeight()
            .shimmerLoading(),

        ) {}
}