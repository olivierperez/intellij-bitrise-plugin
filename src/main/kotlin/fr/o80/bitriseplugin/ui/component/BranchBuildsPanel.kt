package fr.o80.bitriseplugin.ui.component

import com.intellij.ui.components.panels.VerticalLayout
import com.intellij.ui.scale.JBUIScale
import fr.o80.bitriseplugin.domain.model.Branch
import fr.o80.bitriseplugin.ui.DurationFormatter
import fr.o80.bitriseplugin.ui.bold
import fr.o80.bitriseplugin.ui.padding
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
        add(JLabel(branch.ref).bold().apply {toolTipText = branch.ref})
        add(JComment("$since ago - $duration"))
    }

    init {
        layout = BorderLayout(0, 20)
        add(statusIcon, BorderLayout.WEST)
        add(content, BorderLayout.CENTER)
    }
}
