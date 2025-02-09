package com.bingebuddy.app.ui.screens.discovermovies.upcomingmovies


import android.os.Build
import androidx.annotation.RequiresApi
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

const val TAG = "UpcomingMoviesViewModel"

sealed interface UpcomingMoviesUiState {
    data class Success(val movies: List<DiscoverMovieResultModel>) : UpcomingMoviesUiState
    data class Error(val message: String = "Something went wrong") : UpcomingMoviesUiState
    data object Loading : UpcomingMoviesUiState
}


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class UpcomingMoviesViewmodel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val appStateManager: AppStateManager
) : ViewModel() {

    var uiState: UpcomingMoviesUiState by mutableStateOf(UpcomingMoviesUiState.Loading)
        private set

    init {
        getUpcomingMovies()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getUpcomingMovies() {
        viewModelScope.launch {
            appStateManager.allowExplicitContents.collect {
                uiState = UpcomingMoviesUiState.Loading
                val result = moviesRepository.getUpcomingMovies(includeAdult = it)
                uiState = when (result) {
                    is AsyncResult.Success -> UpcomingMoviesUiState.Success(result.data.results!!)
                    is AsyncResult.Failure -> UpcomingMoviesUiState.Error(result.exception.message ?: "Something went wrong")

                }
            }
        }
    }


}