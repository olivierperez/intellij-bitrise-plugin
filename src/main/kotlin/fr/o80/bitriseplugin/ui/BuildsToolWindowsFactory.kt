package fr.o80.bitriseplugin.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import fr.o80.bitriseplugin.data.BitriseWebService
import fr.o80.bitriseplugin.data.HttpClientProvider
import fr.o80.bitriseplugin.domain.model.GetBranchBuildsUseCase
import kotlinx.coroutines.runBlocking

class BuildsToolWindowsFactory : ToolWindowFactory {
    private val getBranchBuilds = GetBranchBuildsUseCase(BitriseWebService(HttpClientProvider.client))
    private val buildsPanelFactory = BuildsPanelFactory { getBranchBuilds(50) }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        runBlocking {
            val panel = buildsPanelFactory.create(getBranchBuilds(50))
            val content = ContentFactory.getInstance().createContent(panel, null, false)
            toolWindow.contentManager.addContent(content)
        }
    }
}
