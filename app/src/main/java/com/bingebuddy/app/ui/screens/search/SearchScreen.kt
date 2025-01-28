@file:OptIn(ExperimentalMaterial3Api::class)

package com.bingebuddy.app.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBackIosNew
import androidx.compose.material.icons.sharp.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
            contentAlignment = Alignment.Center
        ) {
            Column {
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
                            LazyColumn {
                                items(uiState.searchResults.size) {
                                    val searchResult = uiState.searchResults[it]
                                    Text("${searchResult.title}")
                                }
                            }
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
        CircularProgressIndicator(
        )
    }
}