package fr.o80.bitriseplugin.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Paging(
    @SerialName("total_item_count")
    val totalItemCount: Int,
    @SerialName("page_item_limit")
    val pageItemLimit: Int,
    @SerialName("next")
    val next: String
)
