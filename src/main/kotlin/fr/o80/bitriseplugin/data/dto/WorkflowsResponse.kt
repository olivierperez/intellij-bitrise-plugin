package fr.o80.bitriseplugin.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorkflowsResponse(
    @SerialName("data")
    val names: List<String>
)
