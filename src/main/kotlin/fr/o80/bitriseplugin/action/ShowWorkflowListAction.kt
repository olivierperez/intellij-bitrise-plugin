package fr.o80.bitriseplugin.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import fr.o80.bitriseplugin.ui.entry.BuildsDialog

class ShowWorkflowListAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        BuildsDialog(
            title = "Builds",
            project = project
        ).show()
    }
}
