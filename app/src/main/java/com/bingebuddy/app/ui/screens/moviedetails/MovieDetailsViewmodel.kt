package com.bingebuddy.app.ui.screens.moviedetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.network.HttpException
import com.bingebuddy.app.data.network.repository.MovieDetailsRepository
import com.bingebuddy.app.data.network.model.MovieDetailsModel
import com.bingebuddy.app.ui.screens.discovertvseries.airingtodaytvseries.TAG
import com.bingebuddy.app.utils.AsyncResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject


sealed interface MovieDetailUiState {
    data class Success(val movieDetails: MovieDetailsModel): MovieDetailUiState
    data class Error(val movieId: String?, val message: String): MovieDetailUiState
    data object Loading: MovieDetailUiState
}


@HiltViewModel
class MovieDetailsViewmodel @Inject constructor(
    private val repository: MovieDetailsRepository,
    savedStateHandle: SavedStateHandle,

    ): ViewModel() {

    var uiState: MovieDetailUiState by mutableStateOf(MovieDetailUiState.Loading)
        private set

    init {
        val movieId = savedStateHandle.get<String>("movieId")
        if (movieId != null) {
            getMovieDetails(movieId)
        } else {
            uiState = MovieDetailUiState.Error(null, "Something went wrong")
        }

    }

    fun getMovieDetails(movieId: String) {
        viewModelScope.launch {
            uiState = MovieDetailUiState.Loading
            val result = repository.getMovieDetails(movieId)
            uiState = when (result) {
                is AsyncResult.Success -> {
                    MovieDetailUiState.Success(result.data)
                }
                is AsyncResult.Failure -> {
                    MovieDetailUiState.Error(movieId, result.exception.message ?: "Something went wrong")
                }
            }

        }
    }
}