@file:OptIn(ExperimentalMaterial3Api::class)

package com.bingebuddy.app.ui.screens.moviedetails

import android.graphics.Paint.Align
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew

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
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun MovieDetailScreen(
    navigateUp: () -> Unit,
    navigateTo: (route: String) -> Unit,
    viewmodel: MovieDetailsViewmodel = hiltViewModel()) {

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
                    Text(
                        uiState.movieDetails.toString()
                    )
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

    CircularProgressIndicator()
}