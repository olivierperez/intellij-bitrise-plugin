package fr.o80.bitriseplugin.ui.component

import com.intellij.ui.components.panels.VerticalLayout
import fr.o80.bitriseplugin.domain.model.Branch
import fr.o80.bitriseplugin.ui.DurationFormatter
import fr.o80.bitriseplugin.ui.bold
import fr.o80.bitriseplugin.ui.padding
import fr.o80.bitriseplugin.ui.tooltip
import kotlinx.datetime.Clock
import java.awt.BorderLayout
import javax.swing.JLabel
import javax.swing.JPanel

class BranchBuildsPanel(
    branch: Branch
) : JPanel() {
    private val durationFormatter = DurationFormatter()

    private val statusIcon = JLabel(branch.moreRecentBuild.status.icon).padding(10)

    private val content = JPanel(VerticalLayout(1)).apply {
        val since = durationFormatter.format(Clock.System.now() - branch.moreRecentBuild.startDate)
        val duration = durationFormatter.format(branch.moreRecentBuild.duration)
        add(JLabel(branch.ref).bold().tooltip(branch.ref))
        add(JComment("$since ago - $duration"))
        branch.moreRecentBuild.message?.let { message ->
            add(JComment(message).tooltip(message))
        }
    }

    init {
        layout = BorderLayout(0, 20)
        add(statusIcon, BorderLayout.WEST)
        add(content, BorderLayout.CENTER)
    }
}
