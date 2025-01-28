package com.bingebuddy.app.data.repository

import com.bingebuddy.app.network.TmdbApiService
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val tmdbApiService: TmdbApiService
) {

    suspend fun searchAll(
        query: String,
        page: Int = 1,
        language: String? = null,
        region: String? = null,
        year: Int? = null,
        movieYear: Int? = null,
        tvYear: Int? = null,
        includeAdult: Boolean = false,
    ) = tmdbApiService.searchAll(
        query = query,
        page = page,
        includeAdult = includeAdult,
        language = language,
        region = region,
        year = year,
        primaryReleaseYear = movieYear,
        firstAirDateYear = tvYear
    )
}