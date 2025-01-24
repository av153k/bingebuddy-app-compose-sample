package com.bingebuddy.app.data.model


import com.bingebuddy.app.model.DiscoverMovieResultModel
import kotlinx.serialization.*


@Serializable
data class DiscoverResponseModel<T> (
    val dates: Dates? = null,
    val page: Long? = null,
    val results: List<T>? = null,

    @SerialName("total_pages")
    val totalPages: Long? = null,

    @SerialName("total_results")
    val totalResults: Long? = null
)

@Serializable
data class Dates (
    val maximum: String? = null,
    val minimum: String? = null
)
