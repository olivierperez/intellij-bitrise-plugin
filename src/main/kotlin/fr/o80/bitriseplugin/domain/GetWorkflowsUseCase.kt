package fr.o80.bitriseplugin.domain

import fr.o80.bitriseplugin.data.BitriseWebService

class GetWorkflowsUseCase(
    private val webService: BitriseWebService
) {
    suspend operator fun invoke(): List<String> {
        return webService.listWorkflows().sortedBy { it.lowercase() }
    }
}
