package fr.o80.bitriseplugin.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.panel
import fr.o80.bitriseplugin.data.dto.Build
import javax.swing.JComponent

class BuildsDialog(
    title: String,
    project: Project,
    private val builds: List<Build>
) : DialogWrapper(project) {

    init {
        setTitle(title)
        setOKButtonText("OK")
        init()

        builds
            .map { build -> build.branch ?: build.tag }
            .forEach { ref -> println("- $ref") }
    }

    override fun createCenterPanel(): JComponent = panel {
        row {
            label("")
            label("Ref")
        }

        builds.forEach {build ->
            row {
                label("")
                label(build.ref)
            }
        }
    }
}
