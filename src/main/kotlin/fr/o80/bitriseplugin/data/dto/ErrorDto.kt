package fr.o80.bitriseplugin.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorDto(
    @SerialName("status")
    val status: String,
    @SerialName("message")
    val message: String,
    @SerialName("slug")
    val slug: String,
    @SerialName("service")
    val service: String,
)
