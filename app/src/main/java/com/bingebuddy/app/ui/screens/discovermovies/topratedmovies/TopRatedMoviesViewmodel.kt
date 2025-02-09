package com.bingebuddy.app.ui.screens.discovermovies.topratedmovies


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

const val TAG = "TopRatedMoviesViewModel"

sealed interface TopRatedMoviesUiState {
    data class Success(val movies: List<DiscoverMovieResultModel>) : TopRatedMoviesUiState
    data class Error(val message: String = "Something went wrong") : TopRatedMoviesUiState
    data object Loading : TopRatedMoviesUiState
}


@HiltViewModel
class TopRatedMoviesViewmodel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val appStateManager: AppStateManager
) : ViewModel() {

    var uiState: TopRatedMoviesUiState by mutableStateOf(TopRatedMoviesUiState.Loading)
        private set

    init {
        getTopRatedMovies()
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            appStateManager.allowExplicitContents.collect {
                uiState = TopRatedMoviesUiState.Loading
                val result = moviesRepository.getTopRatedMovies(includeAdult = it)
                uiState = when (result) {
                    is AsyncResult.Success -> TopRatedMoviesUiState.Success(result.data.results!!)
                    is AsyncResult.Failure -> TopRatedMoviesUiState.Error(
                        result.exception.message ?: "Something went wrong"
                    )
                }
            }
        }
    }


}