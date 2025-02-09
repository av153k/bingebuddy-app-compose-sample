package com.bingebuddy.app.data.network.repository

import android.content.Context
import com.bingebuddy.app.data.BaseRepository
import com.bingebuddy.app.data.network.model.MovieDetailsModel
import com.bingebuddy.app.network.TmdbApiService
import com.bingebuddy.app.utils.AsyncResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailsRepository @Inject constructor(
    private val tmdbApiService: TmdbApiService,
    context: Context,
): BaseRepository(
    context = context
) {
    suspend fun getMovieDetails(movieId: String): AsyncResult<MovieDetailsModel> = makeSafeApiCall {
        tmdbApiService.getMovieDetails(movieId);
    }
}