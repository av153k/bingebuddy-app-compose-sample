package com.bingebuddy.app.model

import kotlinx.serialization.*


@Serializable
data class DiscoverMovieResultModel (
    val adult: Boolean? = null,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("genre_ids")
    val genreIDS: List<Long>? = null,

    val id: Long? = null,

    @SerialName("original_language")
    val originalLanguage: String? = null,

    @SerialName("original_title")
    val originalTitle: String? = null,

    val overview: String? = null,
    val popularity: Double? = null,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("release_date")
    val releaseDate: String? = null,

    val title: String? = null,
    val video: Boolean? = null,

    @SerialName("vote_average")
    val voteAverage: Double? = null,

    @SerialName("vote_count")
    val voteCount: Long? = null
)

