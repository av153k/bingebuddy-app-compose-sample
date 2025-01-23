package com.bingebuddy.app.ui.screens.nowplaying

import androidx.compose.runtime.MutableState
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

sealed interface NowPlayingUiState {
    data class Success(val movies: List<DiscoverMovieResultModel>) : NowPlayingUiState
    data object Error : NowPlayingUiState
    data object Loading : NowPlayingUiState
}


@HiltViewModel
class NowPlayingViewmodel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    var uiState: NowPlayingUiState by mutableStateOf(NowPlayingUiState.Loading)
        private set

    init {
        getNowPlayingMovies()
    }

    fun getNowPlayingMovies() {
        viewModelScope.launch {
            uiState = NowPlayingUiState.Loading
            uiState = try {
                val discoverResult = moviesRepository.getNowPlayingMovies()
                NowPlayingUiState.Success(
                    movies = discoverResult.results ?: listOf()
                )
            } catch (e: IOException) {
                Timber.tag(TAG).e(e)
                NowPlayingUiState.Error
            } catch (e: HttpException) {
                Timber.tag(TAG).e(e)
                NowPlayingUiState.Error
            }
        }
    }


}