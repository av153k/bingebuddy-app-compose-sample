package com.bingebuddy.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCountry (
    @SerialName("iso_3166_1")
    val iso3166_1: String? = null,

    val name: String? = null
)
