package fr.o80.bitriseplugin.domain

import fr.o80.bitriseplugin.data.BitriseWebService

class StartWorkflowUseCase(
    private val webService: BitriseWebService
) {
    suspend operator fun invoke(
        workflow: String,
        tag: String?,
        branch: String?
    ) {
        webService.startWorkflow(workflow, tag, branch)
    }
}
