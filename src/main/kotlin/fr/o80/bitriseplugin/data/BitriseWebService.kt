package fr.o80.bitriseplugin.data

import fr.o80.bitriseplugin.Const
import fr.o80.bitriseplugin.data.dto.BuildDto
import fr.o80.bitriseplugin.data.dto.BuildsResponse
import fr.o80.bitriseplugin.data.dto.StartWorkflowPayload
import fr.o80.bitriseplugin.data.dto.WorkflowsResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class BitriseWebService(
    private val client: HttpClient
) {
    suspend fun listBuilds(limit: Int, next: String? = null): List<BuildDto> {
        val response: HttpResponse = client.get("https://api.bitrise.io/v0.1/apps/${Const.appSlug}/builds") {
            header("Authorization", Const.token)

            accept(ContentType.Application.Json)

            parameter("limit", limit)
            parameter("next", next)
        }

        return response.body<BuildsResponse>().data
    }

    suspend fun listWorkflows(): List<String> {
        val response: HttpResponse = client.get("https://api.bitrise.io/v0.1/apps/${Const.appSlug}/build-workflows") {
            header("Authorization", Const.token)

            accept(ContentType.Application.Json)
        }

        return response.body<WorkflowsResponse>().names
    }

    suspend fun startWorkflow(workflow: String, tag: String?, branch: String?) {
        val response: HttpResponse = client.post("https://api.bitrise.io/v0.1/apps/${Const.appSlug}/builds") {
            header("Authorization", Const.token)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            setBody(StartWorkflowPayload.from(workflow, tag, branch))
        }

        println("start workflow response: ${response.body<String>()}")
    }
}
