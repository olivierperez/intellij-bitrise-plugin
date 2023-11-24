package fr.o80.bitriseplugin.domain

import fr.o80.bitriseplugin.data.BitriseWebService
import fr.o80.bitriseplugin.data.dto.BuildDto
import fr.o80.bitriseplugin.domain.model.Branch
import fr.o80.bitriseplugin.domain.model.Build
import fr.o80.bitriseplugin.domain.model.BuildStatus
import kotlinx.datetime.Clock

class GetBranchBuildsUseCase(
    private val webService: BitriseWebService
) {
    suspend operator fun invoke(limit: Int): List<Branch> {
        return webService
            .listBuilds(limit)
            .groupBy(
                keySelector = { it.ref },
                valueTransform = { dto ->
                    Build(
                        startDate = dto.triggeredAt,
                        duration = dto.finishedAt
                            ?.let { finishedAt -> finishedAt - dto.triggeredAt }
                            ?: (Clock.System.now() - dto.triggeredAt),
                        status = BuildStatus.entries.first { it.code == dto.statusCode },
                        message = dto.extractMessage()
                    )
                }
            )
            .map { (ref, builds) -> Branch(ref, builds) }
            .sortedByDescending { branch -> branch.moreRecentBuild.startDate }
    }
}

private fun BuildDto.extractMessage(): String? {
    return when {
        statusCode == BuildStatus.ABORTED_WITH_FAILURE.code -> this.abortReason
        statusCode == BuildStatus.ABORTED_WITH_SUCCESS.code -> this.abortReason
        commitMessage != null -> commitMessage.split("\n\n").first()
        else -> null
    }
}

