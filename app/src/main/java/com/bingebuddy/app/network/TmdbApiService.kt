package com.bingebuddy.app.network

import com.bingebuddy.app.data.model.DiscoverResponseModel
import com.bingebuddy.app.model.DiscoverMovieResultModel
import com.bingebuddy.app.model.DiscoverTvSeriesResultModel
import retrofit2.http.GET

interface TmdbApiService {

    // For movies
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): DiscoverResponseModel<DiscoverMovieResultModel>

    @GET("movie/popular")
    suspend fun getPopularMovies(): DiscoverResponseModel<DiscoverMovieResultModel>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): DiscoverResponseModel<DiscoverMovieResultModel>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): DiscoverResponseModel<DiscoverMovieResultModel>

    // For Tv Series
    @GET("tv/airing_today")
    suspend fun getAiringTodayTvSeries(): DiscoverResponseModel<DiscoverTvSeriesResultModel>

    @GET("tv/popular")
    suspend fun getPopularTvSeries(): DiscoverResponseModel<DiscoverTvSeriesResultModel>

    @GET("tv/top_rated")
    suspend fun getTopRatedTvSeries(): DiscoverResponseModel<DiscoverTvSeriesResultModel>

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTvSeries(): DiscoverResponseModel<DiscoverTvSeriesResultModel>

}