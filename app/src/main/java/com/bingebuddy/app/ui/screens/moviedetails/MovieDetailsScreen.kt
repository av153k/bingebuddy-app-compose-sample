@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package com.bingebuddy.app.ui.screens.moviedetails

import android.graphics.Paint.Align
import android.icu.text.DecimalFormat
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bingebuddy.app.constants.StringConstants
import com.bingebuddy.app.data.PreviewData
import com.bingebuddy.app.data.network.model.GenreModel
import com.bingebuddy.app.data.network.model.MovieDetailsModel
import com.bingebuddy.app.data.network.model.ProductionCompany
import com.bingebuddy.app.data.network.model.ProductionCountry
import com.bingebuddy.app.data.network.model.SpokenLanguage
import com.bingebuddy.app.ui.components.ImageWithShimmer
import com.bingebuddy.app.ui.theme.BingeBuddyTheme
import kotlinx.coroutines.flow.Flow


@Composable
fun MovieDetailScreen(
    navigateUp: () -> Unit,
    navigateTo: (route: String) -> Unit,
    viewmodel: MovieDetailsViewmodel = hiltViewModel()
) {

    val uiState = viewmodel.uiState

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(

                title = {
                    Text("Movie Details")
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
                is MovieDetailUiState.Success -> Box {
                    val movieDetail = uiState.movieDetails
                    MovieDetailContentView(movieDetail)
                }

                is MovieDetailUiState.Error -> RetryView(
                    movieId = uiState.movieId,
                    onRetry = {
                        viewmodel.getMovieDetails(it!!)
                    },
                )

                is MovieDetailUiState.Loading -> LoadingView()
            }
        }
    }
}

@Composable
private fun RetryView(
    movieId: String?,
    onRetry: (movieId: String?) -> Unit, modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Something went wrong", style = MaterialTheme.typography.bodyMedium)
        if (movieId != null) {
            Button(
                onClick = { onRetry(movieId) }
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
private fun MovieDetailContentView(movieDetail: MovieDetailsModel, modifier: Modifier = Modifier) {
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
                        imageUrl = "${StringConstants.IMAGE_BASE_URL}${movieDetail.backdropPath}",
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
                                colors = listOf(Color.Transparent, MaterialTheme.colorScheme.background),
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
                        imageUrl = "${StringConstants.IMAGE_BASE_URL}${movieDetail.posterPath}",
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
                    text = "${movieDetail.title}",
                    style = MaterialTheme.typography.displaySmall,
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
                        Text(text = "Release date", style = MaterialTheme.typography.labelLarge)
                        Spacer(
                            modifier = Modifier.height(5.dp),
                        )
                        Text(
                            text = "${movieDetail.releaseDate}",
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
                                "${DecimalFormat("#.0").format(movieDetail.voteAverage)} (${movieDetail.voteCount})",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }

                }
                Spacer(
                    modifier = Modifier.height(10.dp),
                )

                // Movie genre
                Text(text = "Genre", style = MaterialTheme.typography.labelLarge)
                Spacer(
                    modifier = Modifier.height(5.dp),
                )
                FlowRow {
                    repeat(movieDetail.genres?.size ?: 0) {
                        val genre = (movieDetail.genres ?: listOf())[it]
                        Box {
                            Text(
                                text = "${genre.name}${if (it == (movieDetail.genres?.size ?: 0) - 1) "" else ", "}",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
                Spacer(
                    modifier = Modifier.height(10.dp),
                )

                // Movie overview
                Text(text = "Overview", style = MaterialTheme.typography.labelLarge)
                Spacer(
                    modifier = Modifier.height(5.dp),
                )
                Text(
                    text = "${movieDetail.overview}",
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center,
                )

            }

        }
    }
}


@Preview(showBackground = true)
@Composable
private fun MovieDetailContentPreview() {
    BingeBuddyTheme {
        Scaffold { innerPadding ->
            MovieDetailContentView(
                movieDetail = PreviewData.MOVIE_DETAIL,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

