@file:OptIn(ExperimentalLayoutApi::class)

package com.bingebuddy.app.ui.screens.tvseriesdetails

import android.icu.text.DecimalFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bingebuddy.app.constants.StringConstants
import com.bingebuddy.app.model.CreatedBy
import com.bingebuddy.app.model.Season
import com.bingebuddy.app.model.TvSeriesDetailsModel
import com.bingebuddy.app.ui.components.ImageWithShimmer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvSeriesDetailScreen(
    navigateUp: () -> Unit,
    navigateTo: (route: String) -> Unit,
    viewmodel: TvSeriesDetailsViewmodel = hiltViewModel()
) {

    val uiState = viewmodel.uiState

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(

                title = {
                    Text("Tv Series Details")
                },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (uiState) {
                is TvSeriesDetailUiState.Success -> Box {
                    val tvSeriesDetails = uiState.tvSeriesDetails
                    TvSeriesDetailContentView(tvSeriesDetails)
                }

                is TvSeriesDetailUiState.Error -> RetryView(
                    tvSeriesId = uiState.tvSeriesId,
                    onRetry = {
                        viewmodel.getTvSeriesDetails(it!!)
                    },
                )

                is TvSeriesDetailUiState.Loading -> LoadingView()
            }
        }
    }
}

@Composable
private fun RetryView(
    tvSeriesId: String?,
    onRetry: (tvSeriesId: String?) -> Unit, modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Something went wrong", style = MaterialTheme.typography.bodyMedium)
        if (tvSeriesId != null) {
            Button(
                onClick = { onRetry(tvSeriesId) }
            ) {
                Text("Retry")
            }
        }
    }

}

@Composable
private fun LoadingView(modifier: Modifier = Modifier) {

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
        )
    }
}

@Composable
private fun TvSeriesDetailContentView(
    tvSeriesDetail: TvSeriesDetailsModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Box(modifier = Modifier.height(290.dp)) {
                Box(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                ) {
                    ImageWithShimmer(
                        imageUrl = "${StringConstants.IMAGE_BASE_URL}${tvSeriesDetail.backdropPath}",
                        modifier = Modifier,
                        contentScale = ContentScale.FillWidth,
                    )
                }
                Box(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    MaterialTheme.colorScheme.background
                                ),
                                start = Offset(0f, 0f), // Top center
                                end = Offset(0f, Float.POSITIVE_INFINITY)
                            )
                        )
                ) {

                }

                Box(
                    modifier = Modifier
                        .height(200.dp)
                        .width(150.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    ImageWithShimmer(
                        imageUrl = "${StringConstants.IMAGE_BASE_URL}${tvSeriesDetail.posterPath}",
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }
        item {
            Spacer(
                modifier = Modifier.height(10.dp),
            )
        }
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp)
            ) {
                // Movie title
                Text(
                    text = "${tvSeriesDetail.name}",
                    style = MaterialTheme.typography.displaySmall,
                    textAlign = TextAlign.Center,
                )
                Spacer(
                    modifier = Modifier.height(10.dp),
                )

                if ((tvSeriesDetail.createdBy ?: listOf()).isNotEmpty())
                    CreatorsSection(creators = tvSeriesDetail.createdBy ?: listOf())

                // Tv Series seasons and episodes
                Text(
                    text = "${tvSeriesDetail.numberOfSeasons} Seasons, ${tvSeriesDetail.numberOfEpisodes} Episodes",
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center,
                )
                Spacer(
                    modifier = Modifier.height(10.dp),
                )


                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Movie release
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Pilot air date", style = MaterialTheme.typography.labelLarge)
                        Spacer(
                            modifier = Modifier.height(5.dp),
                        )
                        Text(
                            text = "${tvSeriesDetail.firstAirDate}",
                            style = MaterialTheme.typography.labelMedium,
                            textAlign = TextAlign.Center,
                        )
                    }

                    // Movie release
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Rating", style = MaterialTheme.typography.labelLarge)
                        Spacer(
                            modifier = Modifier.height(5.dp),
                        )
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                Icons.Rounded.Star,
                                contentDescription = "rating icon",
                                tint = Color.Yellow,
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                "${DecimalFormat("#.0").format(tvSeriesDetail.voteAverage)} (${tvSeriesDetail.voteCount})",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }

                }
                Spacer(
                    modifier = Modifier.height(10.dp),
                )


                // Tv Series genre
                Text(text = "Genre", style = MaterialTheme.typography.labelLarge)
                Spacer(
                    modifier = Modifier.height(5.dp),
                )
                FlowRow {
                    repeat(tvSeriesDetail.genres?.size ?: 0) {
                        val genre = (tvSeriesDetail.genres ?: listOf())[it]
                        Box {
                            Text(
                                text = "${genre.name}${if (it == (tvSeriesDetail.genres?.size ?: 0) - 1) "" else ", "}",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
                Spacer(
                    modifier = Modifier.height(10.dp),
                )

                //Tv Series Seasons
                if ((tvSeriesDetail.seasons ?: listOf()).isNotEmpty())
                    SeasonsSection(seasons = tvSeriesDetail.seasons ?: listOf())


                // Tv series overview
                Text(text = "Overview", style = MaterialTheme.typography.labelLarge)
                Spacer(
                    modifier = Modifier.height(5.dp),
                )
                Text(
                    text = "${tvSeriesDetail.overview}",
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center,
                )


            }

        }
    }
}


@Composable
private fun CreatorsSection(creators: List<CreatedBy>) {
    Text(
        text = "Created by",
        style = MaterialTheme.typography.labelLarge,
        textAlign = TextAlign.Center,
    )
    Spacer(
        modifier = Modifier.height(10.dp),
    )
    FlowRow {
        repeat(creators.size) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        color = MaterialTheme.colorScheme.onBackground.copy(
                            alpha = 0.3f
                        )
                    )


            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(5.dp)

                ) {
                    val creator = creators[it]
                    ImageWithShimmer(
                        imageUrl = "${StringConstants.IMAGE_BASE_URL}${creator.profilePath}",
                        modifier = Modifier
                            .height(30.dp)
                            .width(30.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        contentScale = ContentScale.Crop,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        "${creator.name}",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }

    Spacer(
        modifier = Modifier.height(10.dp),
    )
}


@Composable
private fun SeasonsSection(seasons: List<Season>) {
    Text(text = "Seasons", style = MaterialTheme.typography.labelLarge)
    Spacer(
        modifier = Modifier.height(5.dp),
    )
    LazyRow {
        items(seasons) { season ->
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        color = MaterialTheme.colorScheme.onBackground.copy(
                            alpha = 0.2f
                        )
                    )
            )
            {
                Row(modifier = Modifier.padding(5.dp)) {
                    ImageWithShimmer(
                        imageUrl = "${StringConstants.IMAGE_BASE_URL}${season.posterPath}",
                        modifier = Modifier
                            .width(75.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                    Spacer(
                        modifier = Modifier.width(8.dp),
                    )
                    Column(
                        horizontalAlignment = Alignment.Start,
                    ) {

                        Text(
                            "Season ${season.seasonNumber}",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Text(
                            "${season.episodeCount} episodes",
                            style = MaterialTheme.typography.bodySmall,
                        )
                        Text(
                            "Aired on ${season.airDate}",
                            style = MaterialTheme.typography.bodySmall,
                        )
                        Text(
                            "Rating - ${season.voteAverage}",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }

            }
        }
    }
    Spacer(
        modifier = Modifier.height(10.dp),
    )
}



