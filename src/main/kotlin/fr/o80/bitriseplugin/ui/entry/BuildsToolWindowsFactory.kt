package fr.o80.bitriseplugin.ui.entry

import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import fr.o80.bitriseplugin.ui.page.BuildsPage
import fr.o80.bitriseplugin.ui.page.StartWorkflowPage

class BuildsToolWindowsFactory : ToolWindowFactory, DumbAware {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        toolWindow.contentManager.addContent(
            ContentFactory.getInstance().createContent(BuildsPage(), "Builds", false)
        )
        toolWindow.contentManager.addContent(
            ContentFactory.getInstance().createContent(StartWorkflowPage(), "Start Workflow", false)
        )
    }
}
