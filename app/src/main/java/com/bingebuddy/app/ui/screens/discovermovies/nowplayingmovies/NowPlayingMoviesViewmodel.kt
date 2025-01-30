package com.bingebuddy.app.ui.screens.discovermovies.nowplayingmovies

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.network.HttpException
import com.bingebuddy.app.AppStateManager
import com.bingebuddy.app.AppViewmodel
import com.bingebuddy.app.data.network.repository.MoviesRepository
import com.bingebuddy.app.data.network.model.DiscoverMovieResultModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
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


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class NowPlayingMoviesViewmodel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val appStateManager: AppStateManager
) : ViewModel() {

    var uiState: NowPlayingMoviesUiState by mutableStateOf(NowPlayingMoviesUiState.Loading)
        private set

    init {
        getNowPlayingMovies()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getNowPlayingMovies() {
        viewModelScope.launch {
            appStateManager.allowExplicitContents.collect {
                uiState = NowPlayingMoviesUiState.Loading
                uiState = try {
                    val discoverResult = moviesRepository.getNowPlayingMovies(includeAdult = it)
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


}