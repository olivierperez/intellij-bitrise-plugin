package fr.o80.bitriseplugin.domain.model

import fr.o80.bitriseplugin.ui.Icons
import kotlinx.datetime.Instant
import javax.swing.Icon
import kotlin.time.Duration

data class Build(
    val startDate: Instant,
    val duration: Duration,
    val status: BuildStatus,
    val message: String?
)

enum class BuildStatus(val code: Int, val icon: Icon) {
    NOT_FINISHED(0, Icons.CircleGrey),
    SUCCESSFUL(1, Icons.CircleCheck),
    FAILED(2, Icons.CircleMinus),
    ABORTED_WITH_FAILURE(3, Icons.CircleStroke),
    ABORTED_WITH_SUCCESS(4, Icons.CircleStroke)
}
