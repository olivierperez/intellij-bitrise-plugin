package fr.o80.bitriseplugin.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StartWorkflowPayload(
    @SerialName("hook_info")
    val hookInfo: HookInfo,
    @SerialName("build_params")
    val buildParams: BuildParams,
) {
    companion object {
        fun from(workflow: String, tag: String?, branch: String?): StartWorkflowPayload {
            return StartWorkflowPayload(
                hookInfo = HookInfo(type = "bitrise"),
                buildParams = BuildParams(tag, branch, workflow)
            )
        }
    }

    @Serializable
    data class HookInfo(
        @SerialName("type")
        val type: String
    )

    @Serializable
    data class BuildParams(
        @SerialName("tag")
        val tag: String?,
        @SerialName("branch")
        val branch: String?,
        @SerialName("workflow_id")
        val workflowId: String?
    )
}
