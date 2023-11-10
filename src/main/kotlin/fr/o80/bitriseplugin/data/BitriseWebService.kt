package fr.o80.bitriseplugin.data

import fr.o80.bitriseplugin.Const
import fr.o80.bitriseplugin.data.dto.Build
import fr.o80.bitriseplugin.data.dto.BuildsResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class BitriseWebService(
    private val client: HttpClient
) {
    suspend fun listBuilds(limit: Int, next: String? = null): List<Build> {
        val response: HttpResponse = client.get("https://api.bitrise.io/v0.1/apps/${Const.appSlug}/builds") {
            header("Authorization", Const.token)

            accept(ContentType.Application.Json)

            parameter("limit", limit)
            parameter("next", next)
        }

        return response.body<BuildsResponse>().data
    }
}
