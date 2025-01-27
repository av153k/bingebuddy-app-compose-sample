package com.bingebuddy.app.data.repository

import com.bingebuddy.app.data.model.DiscoverResponseModel
import com.bingebuddy.app.model.DiscoverMovieResultModel
import com.bingebuddy.app.model.MovieDetailsModel
import com.bingebuddy.app.network.TmdbApiService
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MoviesRepository @Inject constructor(
    private val tmdbApiService: TmdbApiService
) {

    suspend fun getNowPlayingMovies(): DiscoverResponseModel<DiscoverMovieResultModel> = tmdbApiService.getNowPlayingMovies()

    suspend fun getPopularMovies(): DiscoverResponseModel<DiscoverMovieResultModel> = tmdbApiService.getPopularMovies()

    suspend fun getTopRatedMovies(): DiscoverResponseModel<DiscoverMovieResultModel> = tmdbApiService.getTopRatedMovies()

    suspend fun getUpcomingMovies(): DiscoverResponseModel<DiscoverMovieResultModel> = tmdbApiService.getUpcomingMovies()

}