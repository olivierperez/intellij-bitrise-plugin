package fr.o80.bitriseplugin.ui.page

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

class StartWorkflowPage : JPanel(BorderLayout()) {

    private val webService = BitriseWebService(HttpClientProvider.client)
    private val getWorkflows = GetWorkflowsUseCase(webService)
    private val startWorkflow = StartWorkflowUseCase(webService)
    private val showNotification = ShowNotification()

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private var content: StartWorkflowLoaded? = null
    private var loading: LoadingComponent? = LoadingComponent("Loading Workflows")

    init {
        loading?.let { add(it, BorderLayout.CENTER) }
        load()
    }

    private fun load() {
        scope.launch {
            val workflows = getWorkflows()
            remove(loading)
            loading = null
            StartWorkflowLoaded(workflows, startWorkflow, showNotification)
                .padding(8)
                .let {
                    add(it, BorderLayout.CENTER)
                    it.requestFocus()
                    content = it
                }
        }
    }
}
