package fr.o80.bitriseplugin.data

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
object HttpClientProvider {
    val client: HttpClient by lazy {
        HttpClient(CIO) {
            install(Logging) {
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    explicitNulls = false
                    ignoreUnknownKeys = true
                })
            }
        }
    }
}
