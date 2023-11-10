package com.github.olivierperez.bitriseplugin.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MeResponse(
    @SerialName("data")
    val data: MeData,
)

@Serializable
data class MeData(
    @SerialName("username")
    val username: String,
    @SerialName("slug")
    val slug: String,
    @SerialName("email")
    val email: String,
    @SerialName("avatar_url")
    val avatarUrl: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("has_used_organization_trial")
    val hasUsedOrganizationTrial: Boolean,
    @SerialName("data_id")
    val dataId: Long,
    @SerialName("payment_processor")
    val paymentProcessor: String,
)
