package fr.o80.bitriseplugin.domain

import fr.o80.bitriseplugin.data.BitriseWebService
import fr.o80.bitriseplugin.data.dto.BuildDto
import fr.o80.bitriseplugin.domain.model.Branch
import fr.o80.bitriseplugin.domain.model.Build
import fr.o80.bitriseplugin.domain.model.BuildStatus
import fr.o80.bitriseplugin.ui.utils.throttleFirst
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import kotlinx.datetime.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

private val autoRefreshInterval: Duration = 30.minutes

class ObserveBranchBuildsUseCase(
    private val webService: BitriseWebService
) {

    private val trigger = MutableSharedFlow<Long>()

    @OptIn(FlowPreview::class)
    suspend operator fun invoke(limit: Int): Flow<BranchState> {
        return merge(
            trigger,
            flow {
                while (true) {
                    emit(System.currentTimeMillis())
                    delay(autoRefreshInterval)
                }
            }
        )
            .throttleFirst(2_000)
            .flatMapConcat {
                flow {
                    emit(BranchState.Loading)
                    emit(BranchState.Loaded(loadBranches(limit)))
                }
            }
    }

    suspend fun refresh() {
        trigger.emit(System.currentTimeMillis())
    }

    private suspend fun loadBranches(limit: Int): List<Branch> =
        webService
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

sealed interface BranchState {
    data object Loading : BranchState
    class Loaded(val branches: List<Branch>) : BranchState
}

private fun BuildDto.extractMessage(): String? {
    return when {
        statusCode == BuildStatus.ABORTED_WITH_FAILURE.code -> this.abortReason
        statusCode == BuildStatus.ABORTED_WITH_SUCCESS.code -> this.abortReason
        commitMessage != null -> commitMessage.split("\n\n").first()
        else -> null
    }
}
