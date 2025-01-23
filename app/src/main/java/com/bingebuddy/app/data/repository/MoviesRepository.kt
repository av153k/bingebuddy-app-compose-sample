package com.bingebuddy.app.data.repository

import com.bingebuddy.app.data.model.DiscoverResponseModel
import com.bingebuddy.app.network.TmdbApiService
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MoviesRepository @Inject constructor(
    private val tmdbApiService: TmdbApiService
) {

    suspend fun getNowPlayingMovies(): DiscoverResponseModel = tmdbApiService.getNowPlayingMovies()

}