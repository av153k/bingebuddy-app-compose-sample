package com.bingebuddy.app.data.network.repository

import com.bingebuddy.app.data.network.model.MovieDetailsModel
import com.bingebuddy.app.network.TmdbApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailsRepository @Inject constructor(
    private val tmdbApiService: TmdbApiService
) {
    suspend fun getMovieDetails(movieId: String): MovieDetailsModel = tmdbApiService.getMovieDetails(movieId)
}