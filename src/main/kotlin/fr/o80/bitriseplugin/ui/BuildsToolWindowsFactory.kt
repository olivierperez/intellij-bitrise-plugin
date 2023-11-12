package fr.o80.bitriseplugin.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import fr.o80.bitriseplugin.data.BitriseWebService
import fr.o80.bitriseplugin.data.HttpClientProvider
import fr.o80.bitriseplugin.domain.model.GetBranchBuildsUseCase
import kotlinx.coroutines.runBlocking

class BuildsToolWindowsFactory: ToolWindowFactory {
    private val buildsPanelFactory = BuildsPanelFactory()

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        runBlocking {
            val getBranchBuilds = GetBranchBuildsUseCase(BitriseWebService(HttpClientProvider.client))
            val builds = getBranchBuilds(50)

            val panel = buildsPanelFactory.create(builds)
            val content = ContentFactory.getInstance().createContent(panel, null, false)
            toolWindow.contentManager.addContent(content)
        }
    }
}
