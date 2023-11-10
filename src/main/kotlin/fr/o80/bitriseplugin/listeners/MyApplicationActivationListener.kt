package fr.o80.bitriseplugin.listeners

import fr.o80.bitriseplugin.Const
import fr.o80.bitriseplugin.data.HttpClientProvider
import fr.o80.bitriseplugin.data.dto.MeResponse
import com.intellij.openapi.application.ApplicationActivationListener
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.wm.IdeFrame
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking

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
