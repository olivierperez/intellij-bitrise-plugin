package fr.o80.bitriseplugin.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

class BuildsToolWindowsFactory : ToolWindowFactory {
    private val buildsPanelFactory = BuildsPanelFactory()

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val panel = buildsPanelFactory.create()
        val content = ContentFactory.getInstance().createContent(panel, null, false)
        toolWindow.contentManager.addContent(content)
    }
}
