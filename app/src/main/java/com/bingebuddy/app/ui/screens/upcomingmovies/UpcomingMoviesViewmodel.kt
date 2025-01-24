package com.bingebuddy.app.ui.screens.upcomingmovies


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

const val TAG = "UpcomingMoviesViewModel"

sealed interface UpcomingMoviesUiState {
    data class Success(val movies: List<DiscoverMovieResultModel>) : UpcomingMoviesUiState
    data object Error : UpcomingMoviesUiState
    data object Loading : UpcomingMoviesUiState
}


@HiltViewModel
class UpcomingMoviesViewmodel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    var uiState: UpcomingMoviesUiState by mutableStateOf(UpcomingMoviesUiState.Loading)
        private set

    init {
        getUpcomingMovies()
    }

    fun getUpcomingMovies() {
        viewModelScope.launch {
            uiState = UpcomingMoviesUiState.Loading
            uiState = try {
                val discoverResult = moviesRepository.getUpcomingMovies()
                UpcomingMoviesUiState.Success(
                    movies = discoverResult.results ?: listOf()
                )
            } catch (e: IOException) {
                Timber.tag(TAG).e(e)
                UpcomingMoviesUiState.Error
            } catch (e: HttpException) {
                Timber.tag(TAG).e(e)
                UpcomingMoviesUiState.Error
            }
        }
    }


}