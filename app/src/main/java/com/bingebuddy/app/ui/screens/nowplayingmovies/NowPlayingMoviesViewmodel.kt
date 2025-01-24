package com.bingebuddy.app.ui.screens.nowplayingmovies

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

const val TAG = "NowPlayingViewModel"

sealed interface NowPlayingMoviesUiState {
    data class Success(val movies: List<DiscoverMovieResultModel>) : NowPlayingMoviesUiState
    data object Error : NowPlayingMoviesUiState
    data object Loading : NowPlayingMoviesUiState
}


@HiltViewModel
class NowPlayingMoviesViewmodel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    var uiState: NowPlayingMoviesUiState by mutableStateOf(NowPlayingMoviesUiState.Loading)
        private set

    init {
        getNowPlayingMovies()
    }

    fun getNowPlayingMovies() {
        viewModelScope.launch {
            uiState = NowPlayingMoviesUiState.Loading
            uiState = try {
                val discoverResult = moviesRepository.getNowPlayingMovies()
                NowPlayingMoviesUiState.Success(
                    movies = discoverResult.results ?: listOf()
                )
            } catch (e: IOException) {
                Timber.tag(TAG).e(e)
                NowPlayingMoviesUiState.Error
            } catch (e: HttpException) {
                Timber.tag(TAG).e(e)
                NowPlayingMoviesUiState.Error
            }
        }
    }


}