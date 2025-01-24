package com.bingebuddy.app.data.repository

import com.bingebuddy.app.network.TmdbApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvSeriesRepository @Inject constructor(
    private val tmdbApiService: TmdbApiService
) {

}