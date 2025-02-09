package com.bingebuddy.app.ui.screens.discovermovies.popularmovies


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.network.HttpException
import com.bingebuddy.app.AppStateManager
import com.bingebuddy.app.AppViewmodel
import com.bingebuddy.app.data.network.repository.MoviesRepository
import com.bingebuddy.app.data.network.model.DiscoverMovieResultModel
import com.bingebuddy.app.utils.AsyncResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

const val TAG = "PopularMoviesViewModel"

sealed interface PopularMoviesUiState {
    data class Success(val movies: List<DiscoverMovieResultModel>) : PopularMoviesUiState
    data class Error(val message: String = "Something went wrong") : PopularMoviesUiState
    data object Loading : PopularMoviesUiState
}


@HiltViewModel
class PopularMoviesViewmodel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val appStateManager: AppStateManager
) : ViewModel() {

    var uiState: PopularMoviesUiState by mutableStateOf(PopularMoviesUiState.Loading)
        private set

    init {
        getPopularMovies()
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            appStateManager.allowExplicitContents.collect {
                uiState = PopularMoviesUiState.Loading
                val result = moviesRepository.getPopularMovies(includeAdult = it)
                uiState = when (result) {
                    is AsyncResult.Success -> PopularMoviesUiState.Success(result.data.results!!)
                    is AsyncResult.Failure -> PopularMoviesUiState.Error(
                        result.exception.message ?: "Something went wrong"
                    )
                }
            }
        }
    }


}