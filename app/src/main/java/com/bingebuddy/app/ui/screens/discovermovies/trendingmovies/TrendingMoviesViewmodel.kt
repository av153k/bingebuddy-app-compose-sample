package com.bingebuddy.app.ui.screens.discovermovies.trendingmovies


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
import com.bingebuddy.app.data.repository.MoviesRepository
import com.bingebuddy.app.model.DiscoverMovieResultModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

const val TAG = "TrendingMoviesViewModel"

sealed interface TrendingMoviesUiState {
    data class Success(val movies: List<DiscoverMovieResultModel>) : TrendingMoviesUiState
    data object Error : TrendingMoviesUiState
    data object Loading : TrendingMoviesUiState
}


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class TrendingMoviesViewmodel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val appStateManager: AppStateManager
) : ViewModel() {

    var uiState: TrendingMoviesUiState by mutableStateOf(TrendingMoviesUiState.Loading)
        private set

    init {
        getTrendingMovies()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTrendingMovies() {
        viewModelScope.launch {
            appStateManager.allowExplicitContents.collect {
                uiState = TrendingMoviesUiState.Loading
                uiState = try {
                    val discoverResult = moviesRepository.getTrendingMoviesWeek(includeAdult = it)
                    TrendingMoviesUiState.Success(
                        movies = discoverResult.results ?: listOf()
                    )
                } catch (e: IOException) {
                    Timber.tag(TAG).e(e)
                    TrendingMoviesUiState.Error
                } catch (e: HttpException) {
                    Timber.tag(TAG).e(e)
                    TrendingMoviesUiState.Error
                }
            }
        }
    }


}