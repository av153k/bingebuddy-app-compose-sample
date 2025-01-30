package com.bingebuddy.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bingebuddy.app.data.local.model.WatchlistItem
import com.bingebuddy.app.data.toWatchlistItem
import com.bingebuddy.app.ui.screens.watchlist.WatchlistUiState
import com.bingebuddy.app.ui.screens.watchlist.WatchlistViewmodel
import com.bingebuddy.app.utils.snackbar.LocalSnackbarHostState
import kotlinx.coroutines.launch

@Composable
fun AddToWatchlistButton(
    item: WatchlistItem,
    viewmodel: WatchlistViewmodel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val dbState by viewmodel.allData.collectAsStateWithLifecycle()
    val snackbarHostState = LocalSnackbarHostState.current
    val scope = rememberCoroutineScope()

        when (dbState) {
            is WatchlistUiState.Error -> Box {}
            WatchlistUiState.Loading -> Box {}
            is WatchlistUiState.Success -> {
                val dbItem =
                    (dbState as WatchlistUiState.Success).watchlistItems.find { it.tmdbId == item.tmdbId }
                Box(
                    modifier = Modifier
                        .shadow(elevation = 8.dp, shape = CircleShape)
                        .clip(CircleShape)
                        .background(Color.White)
                        .padding(7.dp)
                        .clickable {
                            if (dbItem == null) {
                                viewmodel.insertIntoWatchlist(item)
                                scope.launch {
                                    snackbarHostState.showSnackbar(message = "Added to watchlist!!")
                                }
                            } else {
                                viewmodel.deleteFromWatchlist(dbItem)
                                scope.launch {
                                    snackbarHostState.showSnackbar(message = "Removed from watchlist!!")
                                }
                            }

                        },
                    contentAlignment = Alignment.Center,
                ) {
                    if (dbItem == null) {
                        Icon(
                            Icons.Outlined.Favorite,
                            contentDescription = "un-favorite",
                            tint = Color.Black
                        )
                    } else {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "favorite",
                            tint = Color.Red
                        )
                    }
                }
            }
        }
}