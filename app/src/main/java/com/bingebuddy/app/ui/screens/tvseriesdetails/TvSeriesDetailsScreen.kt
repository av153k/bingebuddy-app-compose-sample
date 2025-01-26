package com.bingebuddy.app.ui.screens.tvseriesdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel

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
                    Text(
                        uiState.tvSeriesDetails.toString()
                    )
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

    CircularProgressIndicator()
}