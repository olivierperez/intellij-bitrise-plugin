package fr.o80.bitriseplugin.domain.model

import fr.o80.bitriseplugin.ui.Icons
import kotlinx.datetime.Instant
import javax.swing.Icon
import kotlin.time.Duration

data class Build(
    val startDate: Instant,
    val duration: Duration,
    val status: BuildStatus
)

enum class BuildStatus(val icon: Icon) {
    NOT_FINISHED(Icons.Circle),
    SUCCESSFUL(Icons.CircleCheck),
    FAILED(Icons.CircleMinus),
    ABORTED_WITH_FAILURE(Icons.Circle),
    ABORTED_WITH_SUCCESS(Icons.Circle)
}
