package com.bingebuddy.app.ui.screens.topratedmovies



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.network.HttpException
import com.bingebuddy.app.data.repository.MoviesRepository
import com.bingebuddy.app.model.DiscoverMovieResultModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

const val TAG = "TopRatedMoviesViewModel"

sealed interface TopRatedMoviesUiState {
    data class Success(val movies: List<DiscoverMovieResultModel>) : TopRatedMoviesUiState
    data object Error : TopRatedMoviesUiState
    data object Loading : TopRatedMoviesUiState
}


@HiltViewModel
class TopRatedMoviesViewmodel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    var uiState: TopRatedMoviesUiState by mutableStateOf(TopRatedMoviesUiState.Loading)
        private set

    init {
        getTopRatedMovies()
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            uiState = TopRatedMoviesUiState.Loading
            uiState = try {
                val discoverResult = moviesRepository.getTopRatedMovies()
                TopRatedMoviesUiState.Success(
                    movies = discoverResult.results ?: listOf()
                )
            } catch (e: IOException) {
                Timber.tag(TAG).e(e)
                TopRatedMoviesUiState.Error
            } catch (e: HttpException) {
                Timber.tag(TAG).e(e)
                TopRatedMoviesUiState.Error
            }
        }
    }


}