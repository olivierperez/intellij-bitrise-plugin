package fr.o80.bitriseplugin.ui

import com.intellij.ui.components.panels.VerticalLayout
import fr.o80.bitriseplugin.data.BitriseWebService
import fr.o80.bitriseplugin.data.HttpClientProvider
import fr.o80.bitriseplugin.domain.GetBranchBuildsUseCase
import fr.o80.bitriseplugin.domain.model.Branch
import fr.o80.bitriseplugin.ui.component.BranchBuildsPanel
import fr.o80.bitriseplugin.ui.component.VerticalComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.awt.Component
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JPanel

class BuildsPanelFactory {

    private val getBranchBuilds = GetBranchBuildsUseCase(BitriseWebService(HttpClientProvider.client))

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private lateinit var parent: JPanel
    private var buildsComponent: Component? = null

    fun create(): JComponent {
        return JPanel(VerticalLayout(0)).apply {
            add(refreshButton())
            buildsComponent = add(newBranchBuildsList(listOf()))
        }.also {
            parent = it
            refresh()
        }
    }

    private fun newBranchBuildsList(builds: List<Branch>) =
        VerticalComponent(
            items = builds,
            gap = 10
        ) { build ->
            BranchBuildsPanel(build)
                .padding(4)
        }

    private fun refreshButton() = JButton("Refresh").apply {
        addActionListener { refresh() }
    }

    private fun refresh() {
        buildsComponent?.let {
            parent.apply {
                remove(buildsComponent)
                updateUI()
            }
            buildsComponent = null
        }

        scope.launch {
            val builds = getBranchBuilds(50)
            buildsComponent = parent.add(newBranchBuildsList(builds))
            parent.updateUI()
        }
    }
}
