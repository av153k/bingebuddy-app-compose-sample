package com.bingebuddy.app.network

import com.bingebuddy.app.data.model.DiscoverResponseModel
import retrofit2.http.GET

interface TmdbApiService {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): DiscoverResponseModel

    @GET("movie/popular")
    suspend fun getPopularMovies(): DiscoverResponseModel

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): DiscoverResponseModel

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): DiscoverResponseModel
}