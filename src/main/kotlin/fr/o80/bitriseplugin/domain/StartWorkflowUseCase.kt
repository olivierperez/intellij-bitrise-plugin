package fr.o80.bitriseplugin.domain

import fr.o80.bitriseplugin.data.BitriseWebService
import fr.o80.bitriseplugin.data.dto.NetworkResponse

class StartWorkflowUseCase(
    private val webService: BitriseWebService
) {
    suspend operator fun invoke(
        workflow: String,
        tag: String?,
        branch: String?
    ): NetworkResponse<Unit> {
        return webService.startWorkflow(workflow, tag, branch)
    }
}
