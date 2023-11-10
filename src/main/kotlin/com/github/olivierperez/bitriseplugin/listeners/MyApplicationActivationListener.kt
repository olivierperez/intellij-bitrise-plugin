package com.github.olivierperez.bitriseplugin.listeners

import com.github.olivierperez.bitriseplugin.Const
import com.github.olivierperez.bitriseplugin.data.dto.HttpClientProvider
import com.github.olivierperez.bitriseplugin.data.dto.MeResponse
import com.intellij.openapi.application.ApplicationActivationListener
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.wm.IdeFrame
import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

internal class MyApplicationActivationListener : ApplicationActivationListener {

    override fun applicationActivated(ideFrame: IdeFrame) {
        thisLogger().warn("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
        runBlocking {
            val httpResponse: HttpResponse = HttpClientProvider.client.get("https://api.bitrise.io/v0.1/me") {
                header("Authorization", Const.token)
            }

            val body: MeResponse = httpResponse.body()
            println("body: $body")
        }
    }
}
