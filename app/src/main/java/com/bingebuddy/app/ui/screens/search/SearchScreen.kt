@file:OptIn(ExperimentalMaterial3Api::class)

package com.bingebuddy.app.ui.screens.search

import android.graphics.Paint.Align
import android.provider.MediaStore.Audio.Media
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowRight
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.sharp.ArrowBackIosNew
import androidx.compose.material.icons.sharp.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bingebuddy.app.constants.StringConstants
import com.bingebuddy.app.model.MediaType
import com.bingebuddy.app.model.SearchResultModel
import com.bingebuddy.app.navigation.BingeBuddyScreens
import com.bingebuddy.app.ui.components.ImageWithShimmer
import com.bingebuddy.app.ui.components.RatingChip
import kotlinx.coroutines.FlowPreview

@OptIn(FlowPreview::class)
@Composable
fun SearchScreen(
    navigateTo: (route: String) -> Unit,
    navigateUp: () -> Unit,
    viewmodel: SearchViewmodel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {

    val searchText by viewmodel.searchQuery.collectAsState()


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Search", style = MaterialTheme.typography.headlineSmall)
                },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(Icons.Sharp.ArrowBackIosNew, contentDescription = "go-back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
                OutlinedTextField(
                    value = searchText,
                    singleLine = true,
                    shape = shapes.large,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorScheme.surface,
                        unfocusedContainerColor = colorScheme.surface,
                        disabledContainerColor = colorScheme.surface,
                    ),
                    onValueChange = viewmodel::updateSearchQuery,
                    label = {
                        Text("Search for movies, tv and people")

                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                Box {
                    when (val uiState = viewmodel.uiState) {
                        is SearchUiState.Error -> RetryView(
                            query = searchText,
                            onRetry = {

                            }
                        )

                        is SearchUiState.Loading -> LoadingView()
                        is SearchUiState.Success -> {
                            SearchContent(navigateTo = navigateTo, onLoadMore = {}, uiState)
                        }

                        SearchUiState.Initial -> Box {

                        }
                    }
                }
            }

        }
    }
}

@Composable
private fun SearchContent(
    navigateTo: (route: String) -> Unit,
    onLoadMore: () -> Unit,
    uiState: SearchUiState.Success
) {
    val listState = rememberLazyListState()
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex == uiState.searchResults.size - 1 && !uiState.isLoadingMore) {
                    onLoadMore()
                }
            }
    }
    LazyColumn(
        state = listState,
    ) {
        items(uiState.searchResults.size) {
            val searchResult = uiState.searchResults[it]
            SearchResultCard(
                searchResult,
                onClick = {
                    when (searchResult.mediaType) {
                        MediaType.Movie -> {
                            navigateTo(BingeBuddyScreens.MovieDetails("${searchResult.id}").route)
                        }

                        MediaType.Tv -> {
                            navigateTo(BingeBuddyScreens.TvSeriesDetails("${searchResult.id}").route)
                        }

                        else -> {
                            navigateTo(BingeBuddyScreens.PeopleDetails("${searchResult.id}").route)
                        }
                    }
                },
            )
        }
        item {
            if (uiState.isLoadingMore) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
private fun SearchResultCard(
    searchResult: SearchResultModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .height(230.dp)
            .clickable {
                onClick()
            }
    ) {
        Card(
            modifier = Modifier
                .height(150.dp)
                .clip(RoundedCornerShape(10.dp))
                .shadow(elevation = 8.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp,
            )

        ) {
            Row(modifier = Modifier.padding(10.dp)) {
                Spacer(modifier = Modifier.width(150.dp))
                Column {
                    Text(
                        searchResult.title ?: searchResult.name ?: searchResult.originalName ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f),
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(
                        modifier = Modifier.height(5.dp)
                    )
                    if (searchResult.releaseDate != null)
                        Column {
                            Text(
                                "Released - ${searchResult.releaseDate}",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )
                        }
                    if (searchResult.firstAirDate != null)
                        Column {
                            Text(
                                "Pilot - ${searchResult.firstAirDate}",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )
                        }
                    if (searchResult.mediaType != MediaType.Person)
                        Column {
                            RatingChip(searchResult.voteAverage, searchResult.voteCount)
                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )
                        }

                    Row {
                        MediaTypeChip(searchResult.mediaType ?: MediaType.Movie)
                        Spacer(modifier = Modifier.weight(1f))

                        Row(modifier = Modifier.clickable {
                            onClick()
                        }) {
                            Text(
                                "View details",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.tertiary,
                                ),
                            )
                            Icon(
                                Icons.AutoMirrored.Sharp.ArrowRight,
                                contentDescription = null,
                                Modifier.size(17.dp),
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                        }

                    }
                }
            }
        }
        Box(Modifier.padding(10.dp)) {
            Card(
                modifier = Modifier
                    .shadow(10.dp, shape = RoundedCornerShape(10.dp))
                    .clip(
                        RoundedCornerShape(10.dp)
                    )

                    .height(200.dp)
                    .width(133.5.dp),


                ) {
                ImageWithShimmer(
                    imageUrl = "${StringConstants.IMAGE_BASE_URL}${searchResult.posterPath}",
                    contentScale = ContentScale.Crop,
                    errorBuilder = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                Icons.Filled.BrokenImage,
                                contentDescription = null,
                                Modifier
                                    .size(50.dp)
                            )
                        }
                    }
                )
            }
        }
    }
}


@Composable
private fun RetryView(
    query: String?,
    onRetry: (query: String?) -> Unit, modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Something went wrong", style = MaterialTheme.typography.bodyMedium)
        if (query != null) {
            Button(
                onClick = { onRetry(query) }
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
        CircularProgressIndicator()
    }
}

@Composable
private fun MediaTypeChip(mediaType: MediaType) {

    Text(
        "  ${mediaType.name}  ",
        style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onBackground),
        modifier = Modifier

            .clip(RoundedCornerShape(10.dp))
            .background(color = if (mediaType == MediaType.Movie) Color.Yellow else if (mediaType == MediaType.Tv) Color.Cyan else Color.Green)
    )

}