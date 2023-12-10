package fr.o80.bitriseplugin.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import com.jetbrains.compose.theme.WidgetTheme
import fr.o80.bitriseplugin.data.BitriseWebService
import fr.o80.bitriseplugin.data.HttpClientProvider
import fr.o80.bitriseplugin.domain.GetWorkflowsUseCase
import fr.o80.bitriseplugin.domain.ShowNotification
import fr.o80.bitriseplugin.domain.StartWorkflowUseCase
import fr.o80.bitriseplugin.ui.atom.LoadingComponent
import fr.o80.bitriseplugin.ui.template.StartWorkflowLoaded
import fr.o80.bitriseplugin.ui.utils.padding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.awt.BorderLayout
import javax.swing.JPanel

class StartWorkflowPage : JPanel() {

    private val webService = BitriseWebService(HttpClientProvider.client)
    private val getWorkflows = GetWorkflowsUseCase(webService)
    private val startWorkflow = StartWorkflowUseCase(webService)
    private val showNotification = ShowNotification()

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private var content: StartWorkflowLoaded? = null
    private var loading: LoadingComponent? = LoadingComponent("Loading Workflows")

    init {
        add(ComposePanel().apply {
            setBounds(
                this@StartWorkflowPage.x,
                this@StartWorkflowPage.y,
                this@StartWorkflowPage.width,
                this@StartWorkflowPage.height
            )
            setContent {
                WidgetTheme(darkTheme = true) {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        Column(Modifier.fillMaxSize()) {
                            Text("Text1")
                            Button(onClick = {
                                println("Clicked")
                            }) {
                                Text("Bouton")
                            }
                            Text("Text2")
                        }


//                        LaunchedEffect(Unit) {
//                    load()
//                        }
                    }
                }
            }
        })
    }

    private suspend fun load() {
        val workflows = getWorkflows()
        StartWorkflowLoaded(workflows, startWorkflow, showNotification)
            .padding(8)
            .let {
                content = it
            }
    }
}
