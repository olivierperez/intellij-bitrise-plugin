package fr.o80.bitriseplugin.action

import fr.o80.bitriseplugin.data.BitriseWebService
import fr.o80.bitriseplugin.data.HttpClientProvider
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import fr.o80.bitriseplugin.domain.model.GetBranchBuildsUseCase
import fr.o80.bitriseplugin.ui.BuildsDialog
import kotlinx.coroutines.runBlocking

class ShowWorkflowListAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        runBlocking {
            println("Builds:\n$")

            val getBranchBuilds = GetBranchBuildsUseCase(BitriseWebService(HttpClientProvider.client))
            val builds = getBranchBuilds(50)

            BuildsDialog("Builds", project, builds).show()
        }
    }
}
