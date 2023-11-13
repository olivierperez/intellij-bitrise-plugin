package fr.o80.bitriseplugin.domain

import fr.o80.bitriseplugin.data.BitriseWebService
import fr.o80.bitriseplugin.domain.model.Branch
import fr.o80.bitriseplugin.domain.model.Build
import fr.o80.bitriseplugin.domain.model.BuildStatus

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
                        duration = dto.finishedAt - dto.triggeredAt,
                        status = BuildStatus.values()[dto.status]
                    )
                }
            )
            .map { (ref, builds) -> Branch(ref, builds) }
            .sortedByDescending { branch -> branch.moreRecentBuild.startDate }
    }
}

