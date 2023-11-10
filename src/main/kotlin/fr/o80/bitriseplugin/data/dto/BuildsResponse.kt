package fr.o80.bitriseplugin.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BuildsResponse(
    @SerialName("data")
    val data: List<Build>,
    @SerialName("paging")
    val paging: Paging
)

@Serializable
data class Build(
    @SerialName("triggered_at")
    val triggeredAt: String,
    @SerialName("started_on_worker_at")
    val startedOnWorkerAt: String,
    @SerialName("environment_prepare_finished_at")
    val environmentPrepareFinishedAt: String,
    @SerialName("finished_at")
    val finishedAt: String,
    @SerialName("slug")
    val slug: String,
    @SerialName("status")
    val status: Int,
    @SerialName("status_text")
    val statusText: String,
    @SerialName("abort_reason")
    val abortReason: String?,
    @SerialName("is_on_hold")
    val isOnHold: Boolean,
    @SerialName("is_processed")
    val isProcessed: Boolean,
    @SerialName("is_status_sent")
    val isStatusSent: Boolean,
    @SerialName("branch")
    val branch: String?,
    @SerialName("build_number")
    val buildNumber: Int,
    @SerialName("commit_hash")
    val commitHash: String?,
    @SerialName("commit_message")
    val commitMessage: String?,
    @SerialName("tag")
    val tag: String?,
    @SerialName("triggered_workflow")
    val triggeredWorkflow: String,
    @SerialName("triggered_by")
    val triggeredBy: String?,
    @SerialName("machine_type_id")
    val machineTypeId: String?,
    @SerialName("stack_identifier")
    val stackIdentifier: String?,
    @SerialName("original_build_params")
    val originalBuildParams: OriginalBuildParams,
    @SerialName("pipeline_workflow_id")
    val pipelineWorkflowId: Int?,
    @SerialName("pull_request_id")
    val pullRequestId: Int,
    @SerialName("pull_request_target_branch")
    val pullRequestTargetBranch: String?,
    @SerialName("pull_request_view_url")
    val pullRequestViewUrl: String?,
    @SerialName("commit_view_url")
    val commitViewUrl: String?,
    @SerialName("credit_cost")
    val creditCost: Int?,
    @SerialName("log_format")
    val logFormat: String
) {
    val ref: String get() = tag ?: branch ?: commitHash ?: commitMessage ?: "no-ref"

    @Serializable
    data class OriginalBuildParams(
        @SerialName("commit_hash")
        val commitHash: String?,
        @SerialName("commit_message")
        val commitMessage: String?,
        @SerialName("tag")
        val tag: String?,
        @SerialName("branch")
        val branch: String?,
        @SerialName("branch_repo_owner")
        val branchRepoOwner: String?,
        @SerialName("branch_dest")
        val branchDest: String?,
        @SerialName("branch_dest_repo_owner")
        val branchDestRepoOwner: String?,
        @SerialName("workflow_id")
        val workflowId: String?,
        @SerialName("pull_request_id")
        val pullRequestId: Int?,
        @SerialName("pull_request_repository_url")
        val pullRequestRepositoryUrl: String?,
        @SerialName("base_repository_url")
        val baseRepositoryUrl: String?,
        @SerialName("head_repository_url")
        val headRepositoryUrl: String?,
        @SerialName("pull_request_unverified_merge_branch")
        val pullRequestUnverifiedMergeBranch: String?,
        @SerialName("pull_request_head_branch")
        val pullRequestHeadBranch: String?,
        @SerialName("pull_request_author")
        val pullRequestAuthor: String?,
        @SerialName("check_run_id")
        val checkRunId: Long?
    )
}
