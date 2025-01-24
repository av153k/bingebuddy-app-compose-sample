package com.bingebuddy.app.model

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class DiscoverTvSeriesResultModel(
    val adult: Boolean? = null,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("genre_ids")
    val genreIDS: List<Long>? = null,

    val id: Long? = null,

    @SerialName("origin_country")
    val originCountry: List<String>? = null,

    @SerialName("original_language")
    val originalLanguage: String? = null,

    @SerialName("original_name")
    val originalName: String? = null,

    val overview: String? = null,
    val popularity: Double? = null,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("first_air_date")
    val firstAirDate: String? = null,

    val name: String? = null,

    @SerialName("vote_average")
    val voteAverage: Double? = null,

    @SerialName("vote_count")
    val voteCount: Long? = null
)
