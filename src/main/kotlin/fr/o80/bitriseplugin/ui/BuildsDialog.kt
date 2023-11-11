package fr.o80.bitriseplugin.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.panel
import fr.o80.bitriseplugin.domain.model.Branch
import kotlinx.datetime.Clock
import javax.swing.JComponent

class BuildsDialog(
    title: String,
    project: Project,
    private val builds: List<Branch>
) : DialogWrapper(project) {

    private val durationFormatter = DurationFormatter()

    init {
        setTitle(title)
        setOKButtonText("OK")
        init()

        builds
            .map { build -> build.ref }
            .forEach { ref -> println("- $ref") }
    }

    override fun createCenterPanel(): JComponent = realPanel()

    private fun realPanel() = panel {
        builds.forEach { build ->
            val since = Clock.System.now() - build.moreRecentBuild.startDate
            twoColumnsRow(
                column1 = { text(build.ref).bold() },
                column2 = { icon(build.moreRecentBuild.status.icon) },
            ).rowComment(durationFormatter.format(since))
        }
    }
}
