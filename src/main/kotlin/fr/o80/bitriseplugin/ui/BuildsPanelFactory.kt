package fr.o80.bitriseplugin.ui

import com.intellij.ui.components.JBScrollPane
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
import java.awt.BorderLayout
import java.awt.Component
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JPanel

class BuildsPanelFactory {

    private val getBranchBuilds = GetBranchBuildsUseCase(BitriseWebService(HttpClientProvider.client))

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private lateinit var parent: JPanel
    private var buildsComponent: Component = newBranchBuildsList()

    fun create(): JComponent {
        return JPanel(BorderLayout()).apply {
            add(refreshButton(), BorderLayout.NORTH)
            add(buildsComponent, BorderLayout.CENTER)
        }.also {
            parent = it
            refresh()
        }
    }

    private fun newBranchBuildsList(builds: List<Branch> = emptyList()) =
        JBScrollPane(
            VerticalComponent(
                items = builds
            ) { build ->
                BranchBuildsPanel(build)
                    .padding(horizontal = 4, vertical = 10)
            }
        ).apply {
            verticalScrollBarPolicy = JBScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
            horizontalScrollBarPolicy = JBScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        }

    private fun refreshButton() = JButton("Refresh").apply {
        addActionListener { refresh() }
    }

    private fun refresh() {
        parent.apply {
            remove(buildsComponent)
            updateUI()
        }
        scope.launch {
            val builds = getBranchBuilds(50)
            buildsComponent = newBranchBuildsList(builds)
            parent.add(buildsComponent, BorderLayout.CENTER)
            parent.updateUI()
        }
    }
}
